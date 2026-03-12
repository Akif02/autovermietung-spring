package com.example.Autovermietung.dto.auto;

import com.example.Autovermietung.enums.CarStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateAutoRequest (
       @Min(1)BigDecimal pricePerDay,
        @NotNull CarStatus status,
        @NotBlank String licensePlate
        ){}


