package com.thedrone.app.services;

import com.thedrone.app.dtos.CreateMedicationRequest;
import com.thedrone.app.dtos.CustomResponse;
import com.thedrone.app.models.Medication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;

import static com.thedrone.app.helper.Validator.isOnlyLettersNumbersDashAndUnderscore;
import static com.thedrone.app.helper.Validator.isOnlyUpperCaseLettersNumbersAndUnderscore;

@Service
public class MedicationService extends BaseService {

    @Transactional
    public void initRecordMedication() {
        try {
            var medications = new ArrayList<Medication>();

            medications.add(Medication.builder()
                    .name("Medication 1")
                    .weight(150)
                    .code("MDC001")
                    .image("test image")
                    .build());
            medications.add(Medication.builder()
                    .name("Medication 2")
                    .weight(350)
                    .code("MDC002")
                    .image("test image")
                    .build());
            medications.add(Medication.builder()
                    .name("Medication 3")
                    .weight(450)
                    .code("MDC003")
                    .image("test image")
                    .build());
            medications.add(Medication.builder()
                    .name("Medication 4")
                    .weight(100)
                    .code("MDC004")
                    .image("test image")
                    .build());
            medications.add(Medication.builder()
                    .name("Medication 5")
                    .weight(390)
                    .code("MDC005")
                    .image("test image")
                    .build());
            medications.add(Medication.builder()
                    .name("Medication 6")
                    .weight(499)
                    .code("MDC006")
                    .image("test image")
                    .build());

            medicationRepository.saveAll(medications);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public ResponseEntity<CustomResponse> createMedication(CreateMedicationRequest createMedicationRequest) {
        try {
            var response = new CustomResponse();

            if (!isOnlyLettersNumbersDashAndUnderscore(createMedicationRequest.getName())) {
                response.setMessage("medication name only allow letters, numbers, underscore and dash.");
                response.setData(null);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (!isOnlyUpperCaseLettersNumbersAndUnderscore(createMedicationRequest.getCode())) {
                response.setMessage("medication code only allow upper case letters, numbers and underscore.");
                response.setData(null);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (Objects.nonNull(medicationRepository.findFirstByCode(createMedicationRequest.getCode()))) {
                response.setMessage("medication code already used by other medication.");
                response.setData(null);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            var medication = Medication.builder()
                    .weight(createMedicationRequest.getWeight())
                    .code(createMedicationRequest.getCode())
                    .name(createMedicationRequest.getName())
                    .image(createMedicationRequest.getImage())
                    .build();

            var result = medicationRepository.save(medication);

            response.setMessage(null);
            response.setData(result);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();

            var response = new CustomResponse();
            response.setMessage(e.getMessage());
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
