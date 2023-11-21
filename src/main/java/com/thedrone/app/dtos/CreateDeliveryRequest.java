package com.thedrone.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDeliveryRequest {

    private Integer droneId;

    private Set<DeliveryItemRequest> deliveryItemRequests;
}
