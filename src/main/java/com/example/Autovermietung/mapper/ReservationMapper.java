package com.example.Autovermietung.mapper;

import com.example.Autovermietung.Entities.Reservation;
import com.example.Autovermietung.dto.reservation.ReservationResponse;

public class ReservationMapper {

    private ReservationMapper() {
    }

    public static ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getAuto(),
                reservation.getUser(),
                reservation.getStatus(),
                reservation.getStartDateTime(),
                reservation.getEndDateTime(),
                reservation.getTotalPrice()
        );
    }
}
