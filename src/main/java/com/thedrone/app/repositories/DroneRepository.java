package com.thedrone.app.repositories;

import com.thedrone.app.models.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Integer> {
    Drone findFirstBySerialNumber(String serialNumber);
    List<Drone> findAllByState(String state);
}
