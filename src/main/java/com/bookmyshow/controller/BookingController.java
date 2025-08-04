package com.bookmyshow.controller;

import com.bookmyshow.dto.BookingRequest;
import com.bookmyshow.model.Booking;
import com.bookmyshow.model.Movie;
import com.bookmyshow.service.BookingService;
import com.bookmyshow.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Map<String, Object> payload) {
        // Extract fields from payload
        Long userId = Long.valueOf(payload.get("userId").toString());
        String movieTitle = payload.get("movieTitle").toString();
        Long theatreId = Long.valueOf(payload.get("theatreId").toString());
        String bookingTimeStr = payload.get("bookingTime").toString();

        // Find movie by title
        Movie movie = movieService.getAllMovies().stream()
            .filter(m -> m.getTitle().equalsIgnoreCase(movieTitle))
            .findFirst()
            .orElse(null);

        if (movie == null) {
            return ResponseEntity.badRequest().body("Movie with title '" + movieTitle + "' not found");
        }

        // Create Booking object
        Booking booking = new Booking();
        booking.setTheatreId(theatreId);
        booking.setMovie(movie);
        booking.setBookingTime(java.time.LocalDateTime.parse(bookingTimeStr));
        // If you use User entity, set user here:
        // booking.setUser(userRepository.findById(userId).orElse(null));
        // If you only have userId as a field, make sure Booking has setUserId(Long userId)
        // Otherwise, remove this line if not needed

        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        Booking booking = bookingService.updateBooking(id, updatedBooking);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        boolean deleted = bookingService.deleteBooking(id);
        if (deleted) {
            return ResponseEntity.ok("Booking deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
