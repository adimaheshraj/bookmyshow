package com.bookmyshow.service;

import com.bookmyshow.model.Booking;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.User;
import com.bookmyshow.repository.BookingRepository;
import com.bookmyshow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MovieRepository movieRepository;

    // ✅ Create booking by movie title and authenticated user
    public Booking createBooking(String movieTitle, User user) {
        if (movieTitle == null || movieTitle.isEmpty()) {
            throw new IllegalArgumentException("Movie title cannot be null or empty");
        }
        if (user == null) {
            throw new IllegalArgumentException("User information is required");
        }

        Movie movie = movieRepository.findByTitle(movieTitle)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found: " + movieTitle));

        Booking booking = new Booking();
        booking.setMovie(movie);
        booking.setUser(user);
        booking.setBookingTime(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    // ✅ Get all bookings by a particular user
    public List<Booking> getBookingsByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User information is required");
        }
        return bookingRepository.findByUser(user);
    }

    // 🔁 Optional: Get all bookings (admin use)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // 🔁 Optional: Delete booking by ID
    public boolean deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

	public Optional<User> getBookingById(Long id) {
		throw new UnsupportedOperationException("Unimplemented method 'getBookingById'");
	}

    public Booking updateBooking(Long id, Booking updatedBooking) {
        throw new UnsupportedOperationException("Unimplemented method 'updateBooking'");
    }

    public Booking createBooking(Booking booking) {
        throw new UnsupportedOperationException("Unimplemented method 'createBooking'");
    }
}
