package com.thedrone.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDroneRequest {
    private String serialNumber;
    private String model;
    private String name;
    private Integer weightCapability;
}
