package com.gomistar.proyecto_gomistar.configuration.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
    
    @Value("${email.username}")
    private String email;

    @Value("${email.password}")
    private String password;

    private Properties getMailProperties() {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return properties;
    }

    @Bean
    JavaMailSender JavaMailSender() {

        JavaMailSenderImpl myMailSender = new JavaMailSenderImpl();
        myMailSender.setJavaMailProperties(getMailProperties());
        myMailSender.setUsername(email);
        myMailSender.setPassword(password);

        return myMailSender;
    }

    @Bean
    ResourceLoader resourceLoader() {
        
        return new DefaultResourceLoader();
    }
}
