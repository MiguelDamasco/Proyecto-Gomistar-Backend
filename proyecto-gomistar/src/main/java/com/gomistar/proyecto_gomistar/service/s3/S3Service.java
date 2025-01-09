package com.gomistar.proyecto_gomistar.service.s3;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class S3Service {
    
    @Value("${download.localpath}")
    private String localpath;

    private final S3Client s3Client;

    private String bucketName = "froggstar11";

    private String bucketNameDownload = "descargas-gomistar";

    private String imageDownloadSource = "https://" + bucketNameDownload +".s3.us-east-2.amazonaws.com/";

    private String imageSource = "https://" + bucketName +".s3.us-east-2.amazonaws.com/";

    public S3Service(S3Client pS3Client) {
        this.s3Client = pS3Client;
    }

    public S3ResponseDTO uploadFile(MultipartFile file) throws IOException {
      
        try {
            String fileName = file.getOriginalFilename();
            String result = imageSource + fileName;
            String contentType = "image/jpeg";
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .contentType(contentType)
            .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            return new S3ResponseDTO(result);
        }
        catch(IOException e) {
            throw new IOException("Error al subir el archivo! " + e.getMessage());

        }
    }

    public S3ResponseDTO uploadDownloadFile(MultipartFile file) throws IOException {
      
        try {
            String fileName = file.getOriginalFilename();
            String result = imageDownloadSource + fileName;
            String contentType = "application/octet-stream";
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketNameDownload)
            .key(fileName)
            .contentType(contentType)
            .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            return new S3ResponseDTO(result);
        }
        catch(IOException e) {
            throw new IOException("Error al subir el archivo! " + e.getMessage());

        }
    }

    private boolean doesObjectExist(String objcectKey) {

        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                                                    .bucket(bucketName)
                                                    .key(objcectKey)
                                                    .build();
            
            s3Client.headObject(headObjectRequest);
        }
        catch (S3Exception e) {
            if(e.statusCode() == 404)
            {
                return false;
            }
        }
        return true;
    }

    private boolean doesObjectDownloadExist(String objcectKey) {

        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                                                    .bucket(bucketNameDownload)
                                                    .key(objcectKey)
                                                    .build();
            
            s3Client.headObject(headObjectRequest);
        }
        catch (S3Exception e) {
            if(e.statusCode() == 404)
            {
                return false;
            }
        }
        return true;
    }

    public String downloadFile(String fileName) throws IOException {

        // Verificar existencia del objeto
        if (!doesObjectExist(fileName)) {
            throw new RequestException("P-299", "El archivo NO existe!");
        }
        
        System.out.println("El archivo existe: " + doesObjectExist(fileName));
        System.out.println("Ruta de descarga: " + localpath + "/" + fileName);
        
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        
        ResponseInputStream<GetObjectResponse> result = null;
        try {
            result = s3Client.getObject(getObjectRequest);
        } catch (S3Exception e) {
            e.printStackTrace(); // Captura errores específicos de S3
            throw new RequestException("P-298", "Error al obtener el archivo: " + e.getMessage());
        }
        
        try (FileOutputStream fos = new FileOutputStream(localpath + "/" + fileName)) {
            byte[] read_buffer = new byte[1024];
            int read_len;
    
            while ((read_len = result.read(read_buffer)) > 0) {
                fos.write(read_buffer, 0, read_len);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Para depuración
            throw new IOException(e.getMessage());
        }
        
        return "Archivo descargado con éxito!!";
    }

    public List<String> listFiles() throws IOException {
        try {
            ListObjectsRequest request = ListObjectsRequest.builder()
                                            .bucket(bucketName)
                                            .build();

            List<S3Object> objects = s3Client.listObjects(request).contents();
            List<String> result = new ArrayList<>();

            for (S3Object object : objects) {
                result.add(object.key());
            }
            return result;
        }
        catch (S3Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public String deletFile(String filename) throws IOException {

        if(!doesObjectExist(filename)) {
            throw new RequestException("P-299", "El archivo NO existe!");
        }

        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                                                            .bucket(bucketName)
                                                            .key(filename)
                                                            .build();

            s3Client.deleteObject(request);
            return "Objeto " + filename + " eliminado con exito";
        }
        catch (S3Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public String deletDownloadFile(String filename) throws IOException {

        if(!doesObjectDownloadExist(filename)) {
            throw new RequestException("P-299", "El archivo NO existe!");
        }

        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                                                            .bucket(bucketNameDownload)
                                                            .key(filename)
                                                            .build();

            s3Client.deleteObject(request);
            return "Objeto " + filename + " eliminado con exito";
        }
        catch (S3Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public String renameFile(String oldFile, String newFile) throws IOException {
        if(!doesObjectExist(oldFile)) {
            throw new RequestException("P-299", "El archivo a renombrar NO existe!");
        }

        try {

            CopyObjectRequest request = CopyObjectRequest.builder()
                                            .destinationBucket(bucketName)
                                            .copySource(bucketName + "/" + oldFile)
                                            .destinationKey(newFile)
                                            .build();
            
            s3Client.copyObject(request);
            deletFile(oldFile);
            return "Archivo renombrado con exito!";
        }
        catch (S3Exception e) {
            throw new IOException(e.getMessage());
        }
    }
    
    public String updateFile(MultipartFile file, String oldFile) throws IOException {
        if(!doesObjectExist(oldFile)) {
            throw new RequestException("P-299", "El archivo NO existe!");
        }

        try {
            String newFileName = file.getOriginalFilename();
            deletFile(oldFile);

            PutObjectRequest request = PutObjectRequest.builder()
                                                        .bucket(bucketName)
                                                        .key(newFileName)
                                                        .build();

            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
            return "Archivo actualizado con exito!!";
                                                        
        }
        catch (S3Exception e)
        {
            throw new IOException(e.getMessage());
        }
    }
}
