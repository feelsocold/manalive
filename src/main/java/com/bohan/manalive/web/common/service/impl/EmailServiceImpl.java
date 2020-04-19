package com.bohan.manalive.web.common.service.impl;

import com.bohan.manalive.web.common.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender sender;

    public int sendSimpleMessage(String to, String subject, String text) throws MailException, MessagingException {
        int randomSix = randomNumber6();

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper =  new MimeMessageHelper(message);
        helper.setFrom("feelsocoold@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(String.valueOf(randomSix));

        try{
        sender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return randomSix;
    }

    public int randomNumber6(){
        double result = (int)Math.floor(Math.random() * 1000000)+100000;
        if(result>1000000){
            result = result - 100000;
        }
        return (int)Math.round(result);
    }
}
