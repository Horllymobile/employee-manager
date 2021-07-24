package com.horllymobile.employeemanager.contracts.request;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateEmployeeRequest {
    private String name;
    private String email;
    private String phone;
    private String jobTitle;
    private String imageUrl;
}
