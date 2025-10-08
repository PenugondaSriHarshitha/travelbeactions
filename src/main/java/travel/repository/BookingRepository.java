package travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travel.model.Booking;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Search by city (e.g., Goa, Hyderabad)
    List<Booking> findByCityContainingIgnoreCase(String city);

    // Search by booking type (e.g., stay, flight, car)
    List<Booking> findByTypeIgnoreCase(String type);

    // Combined search: city + type
    List<Booking> findByCityContainingIgnoreCaseAndTypeIgnoreCase(String city, String type);
}
