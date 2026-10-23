package com.example.commercialcleaningbackend.controller;

import com.example.commercialcleaningbackend.model.Subscriber;
import com.example.commercialcleaningbackend.repository.SubscriberRepository;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/subscribers")
@CrossOrigin(origins = "http://localhost:4200")
public class SubscriberController {

    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final SubscriberRepository repo;

    public SubscriberController(SubscriberRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Subscriber> list() { return repo.findAllByOrderByCreatedAtDesc(); }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String, String> body) {
        String email = clean(body.get("email"));
        if (!EMAIL.matcher(email).matches())
            return ResponseEntity.badRequest().body("Invalid email address.");
        if (repo.existsByEmail(email))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("That email is already on the list.");

        boolean consent = Boolean.parseBoolean(body.getOrDefault("consent", "false"));
        String tag = body.get("tag");

        Subscriber s = new Subscriber();
        s.setEmail(email);
        s.setSource("Manual");
        s.setTag(tag == null || tag.isBlank() ? null : tag.trim());
        s.setStatus(consent ? "active" : "pending");
        return ResponseEntity.ok(repo.save(s));
    }

    @PostMapping("/import")
    public ResponseEntity<?> importCsv(@RequestParam("file") MultipartFile file,
                                       @RequestParam(value = "consent", defaultValue = "false") boolean consent,
                                       @RequestParam(value = "tag", required = false) String tag)
            throws IOException {
        int added = 0, duplicates = 0, invalid = 0;
        Set<String> seen = new HashSet<>();
        String cleanTag = (tag == null || tag.isBlank()) ? null : tag.trim();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Take the first cell on the line that looks like an email (handles headers and extra columns)
                String candidate = null;
                for (String cell : line.split("[,;\\t]")) {
                    String c = clean(cell);
                    if (c.contains("@")) { candidate = c; break; }
                }
                if (candidate == null) continue; // header row or blank line

                if (!EMAIL.matcher(candidate).matches()) { invalid++; continue; }
                if (!seen.add(candidate) || repo.existsByEmail(candidate)) { duplicates++; continue; }

                Subscriber s = new Subscriber();
                s.setEmail(candidate);
                s.setSource("CSV Import");
                s.setTag(cleanTag);
                s.setStatus(consent ? "active" : "pending");
                repo.save(s);
                added++;
            }
        }
        return ResponseEntity.ok(Map.of("added", added, "duplicates", duplicates,
                "invalid", invalid, "status", consent ? "active" : "pending"));
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable Integer id) {
        return repo.findById(id).map(s -> {
            s.setStatus("active");
            return ResponseEntity.ok(repo.save(s));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Link placed in the email footer
    @GetMapping(value = "/unsubscribe", produces = MediaType.TEXT_HTML_VALUE)
    public String unsubscribe(@RequestParam String email) {
        repo.findByEmail(clean(email)).ifPresent(s -> {
            s.setStatus("unsubscribed");
            repo.save(s);
        });
        return "<h2 style='font-family:Arial'>You've been unsubscribed.</h2>"
                + "<p style='font-family:Arial'>You will no longer receive emails from us.</p>";
    }

    private String clean(String s) {
        return s == null ? "" : s.trim().replace("\"", "").replace("'", "").toLowerCase();
    }
}