package travel.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "checkin")
    private LocalDateTime checkin;

    @Column(name = "checkout")
    private LocalDateTime checkout;

    @Column(name = "nights")
    private Integer nights;

    @Column(name = "guests")
    private Integer guests;

    @Column(name = "per_unit", precision = 12, scale = 2)
    private BigDecimal perUnit = BigDecimal.ZERO;

    @Column(name = "extras_cost", precision = 12, scale = 2)
    private BigDecimal extrasCost = BigDecimal.ZERO;

    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(name = "discount", precision = 12, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "taxes", precision = 12, scale = 2)
    private BigDecimal taxes = BigDecimal.ZERO;

    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(name = "status", length = 50)
    private String status = "pending";

    public Booking() {}

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    // --- Getters & Setters ---
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getCheckin() { return checkin; }
    public void setCheckin(LocalDateTime checkin) { this.checkin = checkin; }

    public LocalDateTime getCheckout() { return checkout; }
    public void setCheckout(LocalDateTime checkout) { this.checkout = checkout; }

    public Integer getNights() { return nights; }
    public void setNights(Integer nights) { this.nights = nights; }

    public Integer getGuests() { return guests; }
    public void setGuests(Integer guests) { this.guests = guests; }

    public BigDecimal getPerUnit() { return perUnit; }
    public void setPerUnit(BigDecimal perUnit) { this.perUnit = perUnit; }

    public BigDecimal getExtrasCost() { return extrasCost; }
    public void setExtrasCost(BigDecimal extrasCost) { this.extrasCost = extrasCost; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }

    public BigDecimal getTaxes() { return taxes; }
    public void setTaxes(BigDecimal taxes) { this.taxes = taxes; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
