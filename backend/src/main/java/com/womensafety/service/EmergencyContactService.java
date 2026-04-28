package com.womensafety.service;

import com.womensafety.model.EmergencyContact;
import com.womensafety.model.User;
import com.womensafety.repository.EmergencyContactRepository;
import com.womensafety.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmergencyContactService {

    @Autowired
    private EmergencyContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    // Add emergency contact
    public EmergencyContact addContact(Long userId, EmergencyContact contact) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        contact.setUser(user);
        return contactRepository.save(contact);
    }

    // Get all contacts for a user
    public List<EmergencyContact> getContactsByUserId(Long userId) {
        return contactRepository.findByUserId(userId);
    }

    // Get contact by ID
    public Optional<EmergencyContact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    // Update contact
    public EmergencyContact updateContact(Long id, EmergencyContact contactDetails) {
        EmergencyContact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        
        contact.setName(contactDetails.getName());
        contact.setPhone(contactDetails.getPhone());
        contact.setRelationship(contactDetails.getRelationship());
        
        return contactRepository.save(contact);
    }

    // Delete contact
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}
