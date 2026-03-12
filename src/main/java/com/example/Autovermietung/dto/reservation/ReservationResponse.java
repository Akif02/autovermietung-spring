package com.example.Autovermietung.dto.reservation;

import com.example.Autovermietung.Entities.Auto;
import com.example.Autovermietung.Entities.User;
import com.example.Autovermietung.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationResponse(
        Long id, // ID ist auch praktisch
        Auto auto,
        User user,
        ReservationStatus status,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        BigDecimal totalPrice // Neu
) {
}
