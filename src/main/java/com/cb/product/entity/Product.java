package com.cb.product.entity;

import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("CB_PRODUCT")
public class Product {

    @Id
    private String id;
    @NotBlank
    private String name;
    @NotNull
    @Min(0)
    private BigDecimal price;
    @NotBlank
    private String shortDesc;
    private String longDesc;

    private String thumbnail;
    private String image;
    @CreatedDate
    private LocalDateTime createdOn;
    @LastModifiedDate
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;

    private Boolean active;
}
