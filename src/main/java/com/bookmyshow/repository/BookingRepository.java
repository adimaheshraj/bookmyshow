package com.bookmyshow.repository;

import com.bookmyshow.model.Booking;
import com.bookmyshow.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);

    List<Booking> findByUser(User user);
}
