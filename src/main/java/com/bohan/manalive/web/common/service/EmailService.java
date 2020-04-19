package com.bohan.manalive.web.common.service;

import javax.mail.MessagingException;

public interface EmailService {

    public int sendSimpleMessage(String to, String subject, String text) throws MessagingException;

}
