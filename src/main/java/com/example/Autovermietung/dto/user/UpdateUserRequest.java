package com.example.Autovermietung.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record UpdateUserRequest(
        @NotBlank String name,
        @NotNull @Past(message = "Geburtsdatum muss in der Vergangenheit liegen") LocalDate birthDate,
        @Email @NotBlank String email,
        @NotBlank String phone,
        @NotBlank String address,
        @NotNull Boolean hasDriverLicense
) {
}
