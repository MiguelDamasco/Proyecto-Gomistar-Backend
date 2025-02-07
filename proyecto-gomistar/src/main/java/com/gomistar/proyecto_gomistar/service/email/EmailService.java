package com.gomistar.proyecto_gomistar.service.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.gomistar.proyecto_gomistar.DTO.email.EmailDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {
    
    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender pJavaMailSender, TemplateEngine pTemplateEngine){

        javaMailSender = pJavaMailSender;
        templateEngine = pTemplateEngine;
    }

    public void sendMail(EmailDTO pDTO) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(pDTO.addressee());
        helper.setSubject(pDTO.subject());

        Context context = new Context();
        context.setVariable("message", pDTO.message());
        context.setVariable("title", "Gomistar S.A.");
        String contentHTML = templateEngine.process("email", context);

        helper.setText(contentHTML, true);
        javaMailSender.send(message);
    }
}
