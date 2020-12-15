package com.orion.anibelika.service;

public interface MailSenderService {
    void sendMessage(String to, String subject, String text);
}
