package com.thedrone.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryItemRequest {

    private Integer medicationId;
    private Integer quantity;

}
