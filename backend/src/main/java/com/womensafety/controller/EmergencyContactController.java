package com.womensafety.controller;

import com.womensafety.dto.ApiResponse;
import com.womensafety.model.EmergencyContact;
import com.womensafety.service.EmergencyContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/contacts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmergencyContactController {

    @Autowired
    private EmergencyContactService contactService;

    // Add emergency contact
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse> addContact(@PathVariable Long userId, 
                                                  @Valid @RequestBody EmergencyContact contact) {
        try {
            EmergencyContact savedContact = contactService.addContact(userId, contact);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Contact added successfully", savedContact));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get all contacts for a user
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getContactsByUserId(@PathVariable Long userId) {
        try {
            List<EmergencyContact> contacts = contactService.getContactsByUserId(userId);
            return ResponseEntity.ok(new ApiResponse(true, "Contacts retrieved", contacts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Get contact by ID
    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse> getContactById(@PathVariable Long id) {
        try {
            EmergencyContact contact = contactService.getContactById(id)
                    .orElseThrow(() -> new RuntimeException("Contact not found"));
            return ResponseEntity.ok(new ApiResponse(true, "Contact found", contact));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Update contact
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateContact(@PathVariable Long id, 
                                                     @Valid @RequestBody EmergencyContact contactDetails) {
        try {
            EmergencyContact updatedContact = contactService.updateContact(id, contactDetails);
            return ResponseEntity.ok(new ApiResponse(true, "Contact updated successfully", updatedContact));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    // Delete contact
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteContact(@PathVariable Long id) {
        try {
            contactService.deleteContact(id);
            return ResponseEntity.ok(new ApiResponse(true, "Contact deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
