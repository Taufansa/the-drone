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
@Table(name = "Deliveries")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer totalWeight;

    @ManyToOne
    @JoinColumn(name = "droneId", nullable = true)
    private Drone drone;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<DeliveryItem> deliveryItems;

}
