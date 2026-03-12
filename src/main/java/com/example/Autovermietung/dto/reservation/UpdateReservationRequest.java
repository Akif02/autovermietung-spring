package com.example.Autovermietung.dto.reservation;

import com.example.Autovermietung.enums.ReservationStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateReservationRequest(
        @NotNull ReservationStatus status,
        @NotNull LocalDateTime startDateTime,
        @NotNull LocalDateTime endDateTime

        ) {
}
