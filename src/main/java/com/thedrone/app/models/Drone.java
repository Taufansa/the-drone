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
@Table(name = "Drones")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 100, unique = true)
    private String serialNumber;
    private String model;
    private String name;
    private Integer weightCapability;
    private Integer battery;
    private String state;
    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Delivery> deliveries;


}
