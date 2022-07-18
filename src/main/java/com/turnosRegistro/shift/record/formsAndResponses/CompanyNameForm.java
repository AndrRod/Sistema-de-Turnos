package com.turnosRegistro.shift.record.formsAndResponses;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class CompanyNameForm{
    @NotBlank(message = "cant be null or empty")
    private String companyName;
}