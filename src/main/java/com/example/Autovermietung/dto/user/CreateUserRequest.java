package com.example.Autovermietung.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateUserRequest(
        @NotBlank String name,
        @NotNull LocalDate birthDate,
        @Email @NotBlank String email,
        @NotBlank @Size(min = 8, message = "Passwort muss mindestens 8 Zeichen lang sein") String password,
        @NotBlank String phone,
        @NotBlank String address,
        @NotNull boolean hasDriverLicense
) {
}
