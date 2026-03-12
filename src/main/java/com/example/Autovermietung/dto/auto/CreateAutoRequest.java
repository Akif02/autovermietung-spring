package com.example.Autovermietung.dto.auto;

import com.example.Autovermietung.enums.CarStatus;
import com.example.Autovermietung.enums.Category;
import com.example.Autovermietung.enums.Fuel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateAutoRequest (
        @NotBlank String brand,
        @NotNull Category category,
        @Min(2) Integer seats,
        @Min(1) BigDecimal pricePerDay,
        @NotBlank String transmission,
        @NotBlank String licensePlate,
        @NotNull CarStatus status,
        @NotNull
        Fuel fuel
)
{

}

