package travel.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import travel.model.Travel;
import travel.repository.TravelRepository;
import travel.service.TravelService;

@RestController
@RequestMapping("/Travel")
@CrossOrigin(origins = "http://localhost:5173")
public class TravelController {

    @Autowired
    private TravelService travelService;

    @Autowired
    private TravelRepository travelRepository;

    // ✅ Signup
    @PostMapping("/add")
    public String add(@RequestBody Travel travel) {
        if (travelRepository.existsByEmail(travel.getEmail())) {
            return "Email already registered ❌";
        }
        travelService.saveTravel(travel);
        return "NEW CUSTOMER IS ADDED ✅";
    }

    // ✅ Get all users
    @GetMapping("/getAll")
    public List<Travel> getAllTravels() {
        return travelService.getAllTravels();
    }

    // ✅ Login
    @PostMapping("/login")
    public String login(@RequestBody Travel loginRequest) {
        Optional<Travel> user = travelRepository.findByEmail(loginRequest.getEmail());

        if (user.isPresent()) {
            Travel existingUser = user.get();
            if (existingUser.getPassword().equals(loginRequest.getPassword())) {
                return "Login success ✅";
            } else {
                return "Invalid password ❌";
            }
        } else {
            return "User not found ❌";
        }
    }
}
