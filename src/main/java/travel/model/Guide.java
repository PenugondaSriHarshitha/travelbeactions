package travel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guides")
public class Guide {

    @Id
    @Column(length = 100)
    private String id; // frontend provides id strings (e.g. "g1_x3...")

    @Column(length = 250, nullable = false)
    private String title;

    @Column(length = 200)
    private String location;

    // tags as a simple collection table
    @ElementCollection
    @CollectionTable(name = "guide_tags", joinColumns = @JoinColumn(name = "guide_id"))
    @Column(name = "tag", length = 80)
    private List<String> tags = new ArrayList<>();

    @Column(length = 1024)
    private String thumbnail;

    @Column(length = 1024)
    private String hero;

    @Column(columnDefinition = "TEXT")
    private String excerpt;

    @Column(length = 120)
    private String author;

    @Column(length = 60)
    private String duration;

    private Double rating;

    private Boolean favorite = false;

    @Column(columnDefinition = "TEXT")
    private String directions;

    // coords stored as doubles
    private Double coordsLat;
    private Double coordsLng;

    @Column(length = 40)
    private String status = "published"; // published | pending | rejected

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Guide() {}

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "published";
        if (this.favorite == null) this.favorite = false;
    }

    // Getters & Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public String getHero() { return hero; }
    public void setHero(String hero) { this.hero = hero; }

    public String getExcerpt() { return excerpt; }
    public void setExcerpt(String excerpt) { this.excerpt = excerpt; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Boolean getFavorite() { return favorite; }
    public void setFavorite(Boolean favorite) { this.favorite = favorite; }

    public String getDirections() { return directions; }
    public void setDirections(String directions) { this.directions = directions; }

    public Double getCoordsLat() { return coordsLat; }
    public void setCoordsLat(Double coordsLat) { this.coordsLat = coordsLat; }

    public Double getCoordsLng() { return coordsLng; }
    public void setCoordsLng(Double coordsLng) { this.coordsLng = coordsLng; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
