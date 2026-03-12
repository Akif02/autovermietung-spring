package com.example.Autovermietung.service;

import com.example.Autovermietung.Entities.Auto;
import com.example.Autovermietung.Entities.Reservation;
import com.example.Autovermietung.Entities.User;
import com.example.Autovermietung.dto.reservation.CreateReservationRequest;
import com.example.Autovermietung.dto.reservation.ReservationResponse;
import com.example.Autovermietung.enums.CarStatus;
import com.example.Autovermietung.enums.ReservationStatus;
import com.example.Autovermietung.mapper.ReservationMapper;
import com.example.Autovermietung.repository.AutoRepository;
import com.example.Autovermietung.repository.ReservationRepository;
import com.example.Autovermietung.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service-Klasse für die Geschäftslogik rund um Reservierungen.
 * Behandelt das Erstellen, Stornieren, Löschen und Abrufen von Reservierungen sowie Preisberechnungen.
 * 
 * @author Akif
 */
@Service
public class ReservationService {
    private final ReservationRepository repoReservation;
    private final AutoRepository autoRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository repoReservation, AutoRepository autoRepository, UserRepository userRepository) {
        this.repoReservation = repoReservation;
        this.autoRepository = autoRepository;
        this.userRepository = userRepository;
    }

    /**
     * Erstellt eine neue Reservierung basierend auf den übergebenen Daten.
     * Prüft Verfügbarkeit, berechnet Preis und setzt Auto-Status auf RENTED.
     *
     * @param request Das DTO mit den Reservierungsdetails (AutoID, UserID, Zeiten).
     * @return Die erstellte Reservierung als Response-DTO.
     */
    @Transactional
    public ReservationResponse book(CreateReservationRequest request) {

        Auto auto = autoRepository.findById(request.autoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Auto nicht gefunden"));


        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User nicht gefunden"));


        if (auto.getStatus() == CarStatus.RENTED || auto.getStatus() == CarStatus.MAINTENANCE || auto.getStatus() == CarStatus.OUT_OF_SERVICE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Auto ist nicht verfügbar (Status: " + auto.getStatus() + ")");
        }


        if (request.endDateTime().isBefore(request.startDateTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enddatum muss nach Startdatum liegen");
        }


        long days = ChronoUnit.DAYS.between(request.startDateTime(), request.endDateTime());
        if (days < 1) days = 1;
        BigDecimal totalPrice = auto.getPricePerDay().multiply(BigDecimal.valueOf(days));


        Reservation reservation = new Reservation();
        reservation.setAuto(auto);
        reservation.setUser(user);
        reservation.setStartDateTime(request.startDateTime());
        reservation.setEndDateTime(request.endDateTime());
        reservation.setStatus(ReservationStatus.BOOKED);
        reservation.setTotalPrice(totalPrice);


        auto.setStatus(CarStatus.RENTED);
        autoRepository.save(auto);


        Reservation saved = repoReservation.save(reservation);

        return ReservationMapper.toResponse(saved);
    }

    /**
     * Ruft alle Reservierungen aus der Datenbank ab.
     *
     * @return Eine Liste aller Reservierungen.
     */
    public List<Reservation> getAll() {
        return repoReservation.findAll();
    }

    /**
     * Sucht eine Reservierung anhand ihrer ID.
     *
     * @param id Die ID der gesuchten Reservierung.
     * @return Die gefundene Reservierung.
     * @throws ResponseStatusException (404 NOT FOUND), wenn die Reservierung nicht existiert.
     */
    public Reservation getOne(Long id) {
        return repoReservation.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    /**
     * Storniert eine Reservierung. Setzt Status auf CANCELLED und gibt Auto wieder frei.
     *
     * @param reservationID Die ID der zu stornierenden Reservierung.
     * @return Die aktualisierte Reservierung mit Status CANCELLED.
     * @throws ResponseStatusException (404 NOT FOUND), wenn die Reservierung nicht existiert.
     */
    @Transactional
    public Reservation cancelReservation(Long reservationID) {
        Reservation reservation = repoReservation.findById(reservationID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
        

        reservation.setStatus(ReservationStatus.CANCELLED);
        

        Auto auto = reservation.getAuto();
        auto.setStatus(CarStatus.AVAILABLE);
        autoRepository.save(auto);

        return repoReservation.save(reservation);
    }

    /**
     * Löscht eine Reservierung endgültig aus der Datenbank.
     *
     * @param reservationID Die ID der zu löschenden Reservierung.
     * @throws ResponseStatusException (404 NOT FOUND), wenn die Reservierung nicht existiert.
     */
    public void deleteReservation(Long reservationID) {
        Reservation reservation = repoReservation.findById(reservationID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));

        repoReservation.delete(reservation);
    }

    /**
     * Findet alle Reservierungen eines bestimmten Benutzers.
     *
     * @param userID Die ID des Benutzers.
     * @return Eine Liste der Reservierungen dieses Benutzers.
     */
    public List<Reservation> getByUser(Long userID) {
        return repoReservation.findByUserId(userID);
    }

    /**
     * Berechnet den Gesamtpreis (nur Simulation, speichert nichts).
     */
    public BigDecimal calculateTotalPrice(Long autoID, LocalDateTime start, LocalDateTime end) {
        Auto auto = autoRepository.findById(autoID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Auto nicht gefunden"));
        
        long days = ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate());
        if (days <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End must be after start");
        }
        return auto.getPricePerDay().multiply(BigDecimal.valueOf(days));
    }
}
