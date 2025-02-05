package com.gomistar.proyecto_gomistar.service.lambda;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class LambdaAuxiliarClass {

    public String[] obtenerDatosCedulaIdentidad(String s) {
		
		int[] checked = new int[6];
		String[] resul = new String[8];
		StringBuilder content = new StringBuilder();
		
		for(int i = 0; i < s.length(); i++) {
			content.append(s.charAt(i));
	
			if(content.toString().indexOf("Sobrenome") != -1 && checked[0] == 0) {
				try {
					i = obtenerApellido(s, i, content);
					String apellidoProcesado = limpiarOutputSoloMayuscula(content.toString()).trim();
					resul[0] = apellidoProcesado.substring(0, apellidoProcesado.length() - 1);
					checked[0]++;
					content.setLength(0);
				}
				catch(Exception e) {
					resul[0] = e.getMessage();
				}
			}
			else if(content.toString().indexOf("Nome") != -1 && checked[1] == 0) {
				try {
					i = obtenerNombre(s, i, content);
					String nombreProcesado = limpiarOutputSoloMayuscula(content.toString()).trim();
					resul[1] = nombreProcesado.substring(0, nombreProcesado.length() - 1);
					checked[1]++;
					content.setLength(0);
				}
				catch(Exception e) {
					resul[1] = e.getMessage();
				}
			}
			else if(content.toString().indexOf("Nacionalidade") != -1 && checked[2] == 0) {
				try {
					i = obtenerNacionalidad(s, i, content);
					String nacionalidadProcesado = limpiarOutputSoloMayuscula(content.toString()).trim();
					resul[2] = nacionalidadProcesado.substring(0, nacionalidadProcesado.length() - 1);
					checked[2]++;
					content.setLength(0);
				}
				catch(Exception e) {
					resul[2] = e.getMessage();
				}
			}
			else if(content.toString().indexOf("Data de Nascimento") != -1 && checked[3] == 0) {
				try {
					i = obtenerFechaNacimiento(s, i, content);
					String fechaNacimientoProcesado = limpiarOutputFecha(content.toString());
					resul[3] = fechaNacimientoProcesado.substring(0, fechaNacimientoProcesado.length() - 1).trim();
					checked[3]++;
					content.setLength(0);
				}
				catch(Exception e) {
					resul[3] = e.getMessage();
				}
			}
			else if(content.toString().indexOf("Nascimento") != -1 && checked[4] == 0) {
				try {
					i = obtenerLugarNacimiento(s, i, content);
					String lugarNacimientoProcesado = limpiarOutputSoloMayuscula(content.toString());
					resul[4] = lugarNacimientoProcesado.substring(0, lugarNacimientoProcesado.length() - 1).trim();
					checked[4]++;
					content.setLength(0);
				}
				catch(Exception e) {
					resul[4] = e.getMessage();
				}
			}
			else if((content.toString().indexOf("N de ") != -1 || content.toString().indexOf("N' de") != -1) && checked[5] == 0) {
				try {
					i = obtenerNumeroIdentidad(s, i, content);
					String numeroIdentidadProcesado = limpiarOutpuNumeroIdentidad(content.toString());
					resul[5] = numeroIdentidadProcesado.trim();
					checked[5]++;
					content.setLength(0);
				}
				catch(Exception e) {
					resul[5] = e.getMessage();
				}
			}
			else if(checked[5] != 0) {
				
				try {
					String[] resultDate = obtenerUltimasFechas(s, i);
					resul[6] = resultDate[0];
					resul[7] = resultDate[1];
				}
				catch (Exception e) {
					resul[6] = e.getMessage();
					resul[7] = e.getMessage();
				}
				break;
			}
		
		}
		
		return resul;
	}
	
	public String limpiarOutpuNumeroIdentidad(String s) {
		StringBuilder resul = new StringBuilder();

		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == '.') {
				resul.append('.');
			}
			else if(s.charAt(i) == '-')
			{
				resul.append('-');
			}
			else if (s.charAt(i) >= 48 && s.charAt(i) <= 57) {
				resul.append(s.charAt(i));
			}
		}
		return resul.toString();
	}
	
	public String limpiarOutputSoloMayuscula(String s) {
		StringBuilder resul = new StringBuilder();
		
		for(int i = 0; i < s.length(); i++) {
			if((s.charAt(i) >= 65 && s.charAt(i) <= 90) || (s.charAt(i) == 32)) {
				resul.append(s.charAt(i));
			}
		}
		return resul.toString();
	}
	
	public String limpiarOutputFecha(String s) {
		StringBuilder resul = new StringBuilder();
		
		for(int i = 0; i < s.length(); i++) {
		
		if(s.charAt(i) != '\\' && s.charAt(i) != 'n') {
			resul.append(s.charAt(i));
			}
		}
		return resul.toString();
	}
	
	public int obtenerNombre(String s, int position, StringBuilder sb) {
		
		sb.setLength(0);
		while(sb.toString().indexOf("Nacionalidad") == -1) {
			sb.append(s.charAt(++position));
		}
		
		return position;
	}
	
	public int obtenerApellido(String s, int position, StringBuilder sb) {
		
			sb.setLength(0);
			while(sb.toString().indexOf("Nombre") == -1) {
				sb.append(s.charAt(++position));
			}
		
			return position;
		}

	public int obtenerNacionalidad(String s, int position, StringBuilder sb) {
	
		sb.setLength(0);
		while(sb.toString().indexOf("Fecha de ") == -1) {
			sb.append(s.charAt(++position));
		}

		return position;
	}
	
	public int obtenerFechaNacimiento(String s, int position, StringBuilder sb) {
		
		sb.setLength(0);
		while(sb.toString().indexOf("L") == -1) {
			sb.append(s.charAt(++position));
		}

		return position;
	}
	
	public int obtenerLugarNacimiento(String s, int position, StringBuilder sb) {
		
		sb.setLength(0);
		while(sb.toString().indexOf("Identidad") == -1) {
			sb.append(s.charAt(++position));
		}
		return position;
	}
	
	public int obtenerNumeroIdentidad(String s, int position, StringBuilder sb) {
		
		sb.setLength(0);
		while(sb.toString().indexOf("Expedio") == -1) {
			sb.append(s.charAt(++position));
		}
		return position;
	}
	
	public String extraerFecha(String s, int position)
	{
		StringBuilder resul = new StringBuilder();
		for(int i = 0; i < 10; i++) {
	
			resul.append(s.charAt(++position));
		}
		return resul.toString();
	}
	
	public static int encontrarUltimasFechas(String s, int position) {
		while(s.charAt(position) < 48 && s.charAt(position) > 57) ++position;
		return position;
	}
	
	
	public String[] obtenerUltimasFechas(String s, int position) {
		
		
		String[] result = new String[2];
		StringBuilder sb = new StringBuilder();
		
		for(int i = position; i < s.length(); i++) {
			
			if(s.charAt(i) >= 48 && s.charAt(i) <= 57) {
				sb.append(s.charAt(i));
			}
		}
		
		String firstDate = sb.toString().substring(0, 8);
		String secondDate = sb.toString().substring(8, 16);
		result[0] = getFormatDate(firstDate);
		result[1] = getFormatDate(secondDate);
		return result;
		

	}

	public String getFormatDate(String s) {
	
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < s.length(); i++) {
			
			if(i == 2 || i == 4) {
				sb.append('/');
			}
			sb.append(s.charAt(i));
		}
		
		return sb.toString();
	}
    
    public String convert(String s) {
		
		HashMap<String, Character> map = new HashMap<>();
		map.put("u00c3\\u0093" , 'Á');
		map.put("u00c3\\u00a1" , 'á');
		map.put("u00c3\\u0089" , 'É');
		map.put("u00c3\\u00a9" , 'é');
		map.put("u00c3\\u008d" , 'Í');
		map.put("u00c3\\u00ad" , 'í');
		map.put("u00c3\\u0093" , 'Ó');
		map.put("u00c3\\u00b3" , 'ó');
		map.put("u00c3\\u009a" , 'Ú');
		map.put("u00c3\\u00ba" , 'ú');
		map.put("u00c3\\u0091" , 'Ñ');
		map.put("u00c3\\u00b1" , 'ñ');
		
		StringBuilder resul = new StringBuilder();
		StringBuilder uniCode = new StringBuilder();
		
		int y = 0;
		for(int i = 1; i < s.length(); i++) {
			if(s.charAt(i - 1) == '\\' && s.charAt(i) == 'u') {
				
				for(y = i; y < i + 11; y++) {
					uniCode.append(s.charAt(y));
				}
				i = y;
				if(map.containsKey(uniCode.toString()))
				{
					resul.append(map.get(uniCode.toString()));
				}
				uniCode.setLength(0);
			}
			else {
				resul.append(s.charAt(i - 1));
			}
		}
		return resul.toString();
	}
}
