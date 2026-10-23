package com.example.commercialcleaningbackend.controller; // Update to match your package name

import com.example.commercialcleaningbackend.model.*;
import com.example.commercialcleaningbackend.repository.*;
import com.example.commercialcleaningbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "http://localhost:4200")
public class CampaignController {

    private final CampaignRepository campaignRepo;
    private final SubscriberRepository subscriberRepo;
    private final EmailService emailService;

    @Value("${app.public-url}")
    private String publicUrl;

    public CampaignController(CampaignRepository campaignRepo,
                              SubscriberRepository subscriberRepo,
                              EmailService emailService) {
        this.campaignRepo = campaignRepo;
        this.subscriberRepo = subscriberRepo;
        this.emailService = emailService;
    }

    @GetMapping
    public List<Campaign> list() { return campaignRepo.findAllByOrderByCreatedAtDesc(); }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody Map<String, String> req) {
        String subject = req.get("subject");
        String sender = req.get("senderEmail");
        String html = req.get("htmlContent");

        List<Subscriber> recipients = subscriberRepo.findByStatus("active");
        if (recipients.isEmpty())
            return ResponseEntity.badRequest().body("No active subscribers to send to.");

        int sent = 0, failed = 0;
        for (Subscriber s : recipients) {
            try {
                String unsubscribe = publicUrl + "/api/subscribers/unsubscribe?email="
                        + URLEncoder.encode(s.getEmail(), StandardCharsets.UTF_8);
                String body = html
                        + "<hr><p style='font-size:12px;color:#888'>Oakland Pro Cleaning Services, [YOUR MAILING ADDRESS]<br>"
                        + "<a href='" + unsubscribe + "'>Unsubscribe</a></p>";

                emailService.send(sender, s.getEmail(), subject, body);
                sent++;
                Thread.sleep(80); // stay under SES's default send rate
            } catch (Exception e) {
                failed++;
                System.err.println("Failed to send to " + s.getEmail() + ": " + e.getMessage());
            }
        }

        Campaign c = new Campaign();
        c.setSubject(subject);
        c.setBody(html);
        c.setSentCount(sent);
        campaignRepo.save(c);

        return ResponseEntity.ok("Sent to " + sent + " subscribers" + (failed > 0 ? " (" + failed + " failed)" : "") + ".");
    }
}