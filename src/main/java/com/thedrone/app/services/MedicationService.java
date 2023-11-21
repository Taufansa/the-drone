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
                    .image("https://media.wired.com/photos/62e426ba69d8da74ff881b70/1:1/w_1800,h_1800,c_limit/How-to-Set-Medication-Reminders-On-Your-Phone-Gear-GettyImages-1337249693.jpg")
                    .build());
            medications.add(Medication.builder()
                    .name("Medication 2")
                    .weight(350)
                    .code("MDC002")
                    .image("https://cbassociatetraining.co.uk/wp-content/uploads/2022/07/risk-of-poor-medication-management-scaled.jpg")
                    .build());
            medications.add(Medication.builder()
                    .name("Medication 3")
                    .weight(450)
                    .code("MDC003")
                    .image("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.secondwavemedia.com%2Ffeatures%2Fpolypharmacy11122020.aspx&psig=AOvVaw0auPK_mttsMUgvfY_r1xl-&ust=1700638859790000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCLjq08rL1IIDFQAAAAAdAAAAABAg")
                    .build());
            medications.add(Medication.builder()
                    .name("Medication 4")
                    .weight(100)
                    .code("MDC004")
                    .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5M0VY2BCA9aYVp0vVvkjHEaIHthDzZEWsfg&usqp=CAU")
                    .build());
            medications.add(Medication.builder()
                    .name("Medication 5")
                    .weight(390)
                    .code("MDC005")
                    .image("https://economictimes.indiatimes.com/thumb/msid-96226923,width-1200,height-900,resizemode-4,imgsize-67992/medication.jpg?from=mdr")
                    .build());
            medications.add(Medication.builder()
                    .name("Medication 6")
                    .weight(499)
                    .code("MDC006")
                    .image("https://www.henryford.com/-/media/project/hfhs/henryford/henry-ford-blog/images/mobile-interior-banner-images/2018/09/medication-safety.jpg?h=600&iar=0&w=640&rev=40cd10bb73b1404b9f97ee5d32bf499f&hash=56CF2DCE5E747FC4CC5A038A179F383B")
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
