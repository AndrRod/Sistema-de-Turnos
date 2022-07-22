package com.turnosRegistro.shift.record.formsAndResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter @AllArgsConstructor
public class EmailResponse {
    private String to;
    private String subject;
    private String body;
}
