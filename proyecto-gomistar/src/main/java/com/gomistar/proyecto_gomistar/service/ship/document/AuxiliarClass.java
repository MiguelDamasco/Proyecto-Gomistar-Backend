package com.gomistar.proyecto_gomistar.service.ship.document;

import org.springframework.stereotype.Component;

@Component
public class AuxiliarClass {
    
    public String extractName(String s) {

        StringBuilder sb = new StringBuilder();

        for(int i = s.length() - 1; i >= 0; i--) {

            if(s.charAt(i) == '/') {
                break;
            }

            sb.append(s.charAt(i));
        }

        return reverseString(sb.toString());
    }

    public String reverseString(String s) {

        StringBuilder sb = new StringBuilder();

        for(int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }

        return sb.toString();
    }

    public String getFormat(String s) {

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < s.length(); i++) {

            if(s.charAt(i) == ' ') {
                sb.append('-');
                continue;
            }

            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

}
