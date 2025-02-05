package com.gomistar.proyecto_gomistar.service.lambda;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.gomistar.proyecto_gomistar.DTO.response.user.IdentityCardLectureDTO;

@Service
public class LambdaService {
    
    private final RestTemplate restTemplate;

    private final LambdaAuxiliarClass lambdaAuxiliarClass;

    public LambdaService(RestTemplate pRestTemplate, LambdaAuxiliarClass pLambdaAuxiliarClass) {
        this.restTemplate = pRestTemplate;
        this.lambdaAuxiliarClass = pLambdaAuxiliarClass;
    }

    public IdentityCardLectureDTO getText(String fileName) {
		 // Crear un objeto Map para los parámetros a enviar
        Map<String, String> parametros = new HashMap<>();
        parametros.put("bucket", "froggstar11");
        parametros.put("nombre", fileName);

        // Convertir el Map a JSON manualmente si es necesario (aunque RestTemplate lo hace automáticamente)
        // Crear los encabezados para indicar que se enviará un JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Crear la entidad HttpEntity con el cuerpo (parametros) y los encabezados
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parametros, headers);

        // URL de tu API Gateway que invoca la Lambda
        String url = "https://ay2m3qohde.execute-api.us-east-2.amazonaws.com/textract_service";

        try {
            // Llamar a la API de Lambda usando postForObject
            ResponseEntity<String> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                requestEntity, 
                String.class
            );
            
            // Obtener la respuesta del cuerpo de la respuesta
            String responseBody = response.getBody();
            System.out.println("Response: " + responseBody);

            String myConvert = this.lambdaAuxiliarClass.convert(responseBody);

            String[] myResult = this.lambdaAuxiliarClass.obtenerDatosCedulaIdentidad(myConvert);

            // Crear el objeto userDTO con la información procesada
            IdentityCardLectureDTO myDTO = new IdentityCardLectureDTO("", myResult[1], myResult[0], myResult[2], myResult[5], myResult[3], myResult[6], myResult[7]);
            return myDTO;

        } catch (Exception e) {
            System.err.println("Error al invocar la API de Lambda: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
