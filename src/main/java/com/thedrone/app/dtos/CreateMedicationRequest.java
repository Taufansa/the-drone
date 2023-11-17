package com.thedrone.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMedicationRequest {

    private Integer weight;
    private String code;
    private String name;
    private String image;

}
