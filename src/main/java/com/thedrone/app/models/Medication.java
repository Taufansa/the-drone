package com.thedrone.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Medications")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer medicationId;
    private Integer weight;
    private String code;
    private String name;
    private String image;
    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<DeliveryItem> deliveryItems;

}
