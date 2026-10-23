package com.example.commercialcleaningbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.*;

@Service
public class EmailService {

    private final SesV2Client ses;

    public EmailService(@Value("${aws.region}") String region) {
        this.ses = SesV2Client.builder().region(Region.of(region)).build();
    }

    public void send(String from, String to, String subject, String html) {
        SendEmailRequest request = SendEmailRequest.builder()
                .fromEmailAddress(from)
                .destination(Destination.builder().toAddresses(to).build())
                .content(EmailContent.builder().simple(
                        Message.builder()
                                .subject(Content.builder().data(subject).charset("UTF-8").build())
                                .body(Body.builder()
                                        .html(Content.builder().data(html).charset("UTF-8").build())
                                        .build())
                                .build()
                ).build())
                .build();
        ses.sendEmail(request);
    }
}