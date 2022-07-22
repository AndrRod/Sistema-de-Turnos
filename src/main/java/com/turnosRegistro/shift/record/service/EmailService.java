package com.turnosRegistro.shift.record.service;

import com.sendgrid.Response;

public interface EmailService {
    Response sendEmail(String email, String message, String companyName);
}
