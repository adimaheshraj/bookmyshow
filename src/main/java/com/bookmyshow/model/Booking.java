package com.bookmyshow.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "theatre_id", nullable = false)
    private Long theatreId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")  // ISO format
    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;

    // Constructors (Optional but good to have)
    public Booking() {
    }

    public Booking(Long userId, Movie movie, Long theatreId, LocalDateTime bookingTime) {
        this.user = new User();
        this.user.setId(userId);
        this.movie = movie;
        this.theatreId = theatreId;
        this.bookingTime = bookingTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Long getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(Long theatreId) {
        this.theatreId = theatreId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}