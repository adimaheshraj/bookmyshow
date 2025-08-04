package com.bookmyshow.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long userId;
    private String movieTitle;
    private Long theatreId;
    private String bookingTime;
}
