package travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.model.Guide;
import travel.repository.GuideRepository;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/guides")
@CrossOrigin(origins = "http://localhost:5173")
public class GuideController {

    @Autowired
    private GuideRepository guideRepository;

    // List all (optionally filter by status)
    @GetMapping
    public ResponseEntity<List<Guide>> listAll(@RequestParam(required = false) String status) {
        if (status != null && !status.isBlank()) {
            return ResponseEntity.ok(guideRepository.findByStatusIgnoreCase(status));
        }
        return ResponseEntity.ok(guideRepository.findAll());
    }

    // Get single
    @GetMapping("/{id}")
    public ResponseEntity<Guide> getOne(@PathVariable String id) {
        return guideRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new guide (frontend can submit; status default 'pending' if desired on client)
    @PostMapping
    public ResponseEntity<Guide> create(@RequestBody Guide guide) {
        // if client didn't provide id, create one
        if (guide.getId() == null || guide.getId().isBlank()) {
            guide.setId(UUID.randomUUID().toString());
        }
        // normalize tags: ensure non-null list
        if (guide.getTags() == null) guide.setTags(new ArrayList<>());
        Guide saved = guideRepository.save(guide);
        return ResponseEntity.created(URI.create("/api/guides/" + saved.getId())).body(saved);
    }

    // Update (full replace)
    @PutMapping("/{id}")
    public ResponseEntity<Guide> update(@PathVariable String id, @RequestBody Guide payload) {
        return guideRepository.findById(id).map(existing -> {
            // copy updatable fields
            existing.setTitle(payload.getTitle());
            existing.setLocation(payload.getLocation());
            existing.setExcerpt(payload.getExcerpt());
            existing.setThumbnail(payload.getThumbnail());
            existing.setHero(payload.getHero());
            existing.setAuthor(payload.getAuthor());
            existing.setDuration(payload.getDuration());
            existing.setRating(payload.getRating());
            existing.setFavorite(payload.getFavorite());
            existing.setDirections(payload.getDirections());
            existing.setCoordsLat(payload.getCoordsLat());
            existing.setCoordsLng(payload.getCoordsLng());
            existing.setStatus(payload.getStatus() == null ? existing.getStatus() : payload.getStatus());
            existing.setTags(payload.getTags() == null ? existing.getTags() : payload.getTags());
            Guide saved = guideRepository.save(existing);
            return ResponseEntity.ok(saved);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Partial update for favorite (quick toggle)
    @PatchMapping("/{id}/favorite")
    public ResponseEntity<Guide> toggleFavorite(@PathVariable String id, @RequestParam boolean fav) {
        return guideRepository.findById(id).map(g -> {
            g.setFavorite(fav);
            return ResponseEntity.ok(guideRepository.save(g));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!guideRepository.existsById(id)) return ResponseEntity.notFound().build();
        guideRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Search (q) across title/excerpt/location
    @GetMapping("/search")
    public ResponseEntity<List<Guide>> search(@RequestParam String q) {
        if (q == null || q.trim().isEmpty()) return ResponseEntity.ok(Collections.emptyList());
        List<Guide> found = guideRepository.findByTitleContainingIgnoreCaseOrExcerptContainingIgnoreCaseOrLocationContainingIgnoreCase(q, q, q);
        return ResponseEntity.ok(found);
    }

    // Filter by tag (case-insensitive)
    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<Guide>> byTag(@PathVariable String tag) {
        if (tag == null || tag.isBlank()) return ResponseEntity.ok(Collections.emptyList());
        // naive: pull all and filter tags lower-case (safer)
        List<Guide> all = guideRepository.findAll();
        String lc = tag.toLowerCase(Locale.ROOT);
        List<Guide> filtered = all.stream()
                .filter(g -> (g.getTags() != null && g.getTags().stream().anyMatch(t -> t != null && t.toLowerCase(Locale.ROOT).equals(lc))))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    // Admin-style endpoints for pending queue
    @PostMapping("/{id}/approve")
    public ResponseEntity<Guide> approve(@PathVariable String id) {
        return guideRepository.findById(id).map(g -> {
            g.setStatus("published");
            return ResponseEntity.ok(guideRepository.save(g));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Guide> reject(@PathVariable String id) {
        return guideRepository.findById(id).map(g -> {
            g.setStatus("rejected");
            return ResponseEntity.ok(guideRepository.save(g));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
