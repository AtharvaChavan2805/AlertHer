package com.womensafety.service;

import com.womensafety.model.EmergencyContact;
import com.womensafety.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class SosNotificationService {

    private static final Logger log = LoggerFactory.getLogger(SosNotificationService.class);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Value("${sos.notification.enabled:false}")
    private boolean notificationsEnabled;

    @Value("${sos.notification.twilio.enabled:false}")
    private boolean twilioEnabled;

    @Value("${sos.notification.twilio.account-sid:}")
    private String twilioAccountSid;

    @Value("${sos.notification.twilio.auth-token:}")
    private String twilioAuthToken;

    @Value("${sos.notification.twilio.from-number:}")
    private String twilioFromNumber;

    @Value("${sos.notification.police.enabled:false}")
    private boolean policeNotificationEnabled;

    @Value("${sos.notification.police.numbers:}")
    private String policeNumbersCsv;

    public void dispatchSOS(User user,
                            Double latitude,
                            Double longitude,
                            LocalDateTime alertTime,
                            List<EmergencyContact> contacts) {
        if (!notificationsEnabled) {
            log.warn("SOS notifications are disabled. Set sos.notification.enabled=true to enable real notifications.");
            return;
        }

        List<String> recipients = new ArrayList<>();
        for (EmergencyContact contact : contacts) {
            if (contact.getPhone() != null && !contact.getPhone().isBlank()) {
                recipients.add(contact.getPhone());
            }
        }

        if (policeNotificationEnabled && policeNumbersCsv != null && !policeNumbersCsv.isBlank()) {
            String[] policeNumbers = policeNumbersCsv.split(",");
            for (String number : policeNumbers) {
                if (!number.isBlank()) {
                    recipients.add(number.trim());
                }
            }
        }

        if (recipients.isEmpty()) {
            log.warn("SOS triggered for user {} but no recipients are configured.", user.getId());
            return;
        }

        String message = buildSosMessage(user, latitude, longitude, alertTime);

        int sentCount = 0;
        int failedCount = 0;

        for (String phone : recipients) {
            boolean sent = sendSms(phone, message);
            if (sent) {
                sentCount++;
            } else {
                failedCount++;
            }
        }

        log.info("SOS notification dispatch complete. userId={}, recipients={}, sent={}, failed={}",
                user.getId(), recipients.size(), sentCount, failedCount);
    }

    private String buildSosMessage(User user, Double latitude, Double longitude, LocalDateTime alertTime) {
        String mapsLink = "https://maps.google.com/?q=" + latitude + "," + longitude;
        String formattedTime = alertTime == null ? LocalDateTime.now().format(TIME_FORMATTER) : alertTime.format(TIME_FORMATTER);
        return "SOS ALERT! " + user.getName() + " needs immediate help. "
                + "Location: " + latitude + ", " + longitude + ". "
                + "Map: " + mapsLink + ". "
                + "Time: " + formattedTime;
    }

    private boolean sendSms(String rawPhoneNumber, String message) {
        if (!twilioEnabled) {
            log.warn("Twilio notifications are disabled. Set sos.notification.twilio.enabled=true.");
            return false;
        }

        if (twilioAccountSid == null || twilioAccountSid.isBlank()
                || twilioAuthToken == null || twilioAuthToken.isBlank()
                || twilioFromNumber == null || twilioFromNumber.isBlank()) {
            log.error("Twilio credentials are incomplete. Configure account SID, auth token, and from number.");
            return false;
        }

        String normalizedTo = normalizePhoneNumber(rawPhoneNumber);
        if (normalizedTo == null) {
            log.error("Invalid destination phone number format: {}", rawPhoneNumber);
            return false;
        }

        String normalizedFrom = normalizePhoneNumber(twilioFromNumber);
        if (normalizedFrom == null) {
            log.error("Invalid Twilio from number format: {}", twilioFromNumber);
            return false;
        }

        String formData = "To=" + urlEncode(normalizedTo)
                + "&From=" + urlEncode(normalizedFrom)
                + "&Body=" + urlEncode(message);

        String authValue = twilioAccountSid + ":" + twilioAuthToken;
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString(authValue.getBytes(StandardCharsets.UTF_8));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.twilio.com/2010-04-01/Accounts/" + twilioAccountSid + "/Messages.json"))
                .header(HttpHeaders.AUTHORIZATION, basicAuth)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(formData))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            if (status >= 200 && status < 300) {
                log.info("SOS SMS sent successfully to {}", normalizedTo);
                return true;
            }

            log.error("Twilio SMS failed for {}. status={}, response={}", normalizedTo, status, response.body());
            return false;
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            log.error("Twilio SMS send failed for {}. reason={}", normalizedTo, e.getMessage());
            return false;
        }
    }

    private String normalizePhoneNumber(String rawPhoneNumber) {
        if (rawPhoneNumber == null || rawPhoneNumber.isBlank()) {
            return null;
        }

        String value = rawPhoneNumber.trim().replace(" ", "").replace("-", "");
        if (value.startsWith("+")) {
            String digitsOnly = value.substring(1).replaceAll("\\D", "");
            return digitsOnly.length() >= 8 && digitsOnly.length() <= 15 ? "+" + digitsOnly : null;
        }

        String digits = value.replaceAll("\\D", "");
        if (digits.startsWith("00") && digits.length() > 2) {
            String afterPrefix = digits.substring(2);
            return afterPrefix.length() >= 8 && afterPrefix.length() <= 15 ? "+" + afterPrefix : null;
        }

        if (digits.length() == 10) {
            return "+91" + digits;
        }

        if (digits.length() >= 8 && digits.length() <= 15) {
            return "+" + digits;
        }

        return null;
    }

    private String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}