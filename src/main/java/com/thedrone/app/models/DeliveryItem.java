package com.thedrone.app.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Delivery_Items")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class DeliveryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "deliveryId", nullable = true)
    private Delivery delivery;

    @ManyToOne
    @JoinColumn(name = "medicationId", nullable = true)
    private Medication medication;
}
