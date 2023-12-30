package com.cb.product.record;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductRecord(String id,
        String name,
        BigDecimal price,
        String shortDesc,
        String longDesc,
        String thumbnail,
        String image,
        LocalDateTime createdOn,
        LocalDateTime updatedOn,
        LocalDateTime deletedOn) {
}
