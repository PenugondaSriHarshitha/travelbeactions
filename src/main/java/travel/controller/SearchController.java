package travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import travel.model.Booking;
import travel.repository.BookingRepository;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://localhost:5173")  // allow frontend Vite to connect
public class SearchController {

    @Autowired
    private BookingRepository bookingRepository;

    // Example: /api/search?city=Goa&type=stay
    @GetMapping
    public List<Booking> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String type
    ) {
        if (city != null && type != null)
            return bookingRepository.findByCityContainingIgnoreCaseAndTypeIgnoreCase(city, type);
        else if (city != null)
            return bookingRepository.findByCityContainingIgnoreCase(city);
        else if (type != null)
            return bookingRepository.findByTypeIgnoreCase(type);
        else
            return bookingRepository.findAll();
    }
}
