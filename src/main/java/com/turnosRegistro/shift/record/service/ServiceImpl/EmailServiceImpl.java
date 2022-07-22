package com.turnosRegistro.shift.record.service.ServiceImpl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.turnosRegistro.shift.record.exception.BadRequestException;
import com.turnosRegistro.shift.record.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    SendGrid sendGrid;
    @Value("${email.sender}")
    private String emailSender;
    public Response sendEmail(String email, String message, String companyName) {
        Mail mail = new Mail(new Email(emailSender),
                "Hello, thanks for contact with " + companyName,
                new Email(email),
                new Content("text/plain", message));
        mail.setReplyTo(new Email(emailSender));
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = this.sendGrid.api(request);
        }catch (IOException ex){
            throw new BadRequestException(ex.getMessage());}
        return response;
    }
}
