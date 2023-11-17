package com.thedrone.app.repositories;

import com.thedrone.app.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Integer> {

    Medication findFirstByCode(String code);

}
