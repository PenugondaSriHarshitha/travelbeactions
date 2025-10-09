// src/main/java/travel/controller/SubscriptionController.java
package travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import travel.model.Subscription;
import travel.repository.SubscriptionRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscribe")
@CrossOrigin(origins = "http://localhost:5173")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Subscription subscription) {
        // Auto-generate receiptId if frontend didn't send one
        if (subscription.getReceiptId() == null || subscription.getReceiptId().isEmpty()) {
            subscription.setReceiptId("TF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        Subscription saved = subscriptionRepository.save(subscription);

        // Return receiptId + confirmation URL
        return ResponseEntity.ok().body(new java.util.HashMap<>() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            put("receiptId", saved.getReceiptId());
            put("receiptUrl", "http://localhost:5173/receipt/" + saved.getReceiptId());
            put("status", "ok");
        }});
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> listAll() {
        return ResponseEntity.ok(subscriptionRepository.findAll());
    }
}
