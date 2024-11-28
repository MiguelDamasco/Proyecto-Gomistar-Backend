package com.gomistar.proyecto_gomistar.DTO.response;

import com.gomistar.proyecto_gomistar.model.EmployeeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGetEmployeeDTO {
    
    private String message;

    private EmployeeEntity employeeEntity;

}
