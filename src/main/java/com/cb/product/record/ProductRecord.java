package com.cb.product.record;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductRecord(String id,
                            @NotBlank
                            String name,
                            @NotNull
                            @Min(0)
                            BigDecimal price,
                            @NotBlank
                            String shortDesc,
                            String longDesc,
                            String thumbnail,
                            String image,
                            LocalDateTime createdOn,
                            LocalDateTime updatedOn,
                            LocalDateTime deletedOn, Boolean active) {
}
