package com.thedrone.app.services;

import com.thedrone.app.dtos.CreateMedicationRequest;
import com.thedrone.app.dtos.CustomResponse;
import com.thedrone.app.models.Medication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MedicationServiceTest extends BaseServiceTest {

    @InjectMocks
    private MedicationService medicationService;

    @DisplayName("Success create medication and return created medication")
    @Test
    public void testCreateMedicationAndSuccess() {

        var medication = Medication.builder()
                .name("Medication Test 1")
                .code("MED_TEST_01")
                .weight(120)
                .image("https://media.wired.com/photos/62e426ba69d8da74ff881b70/1:1/w_1800,h_1800,c_limit/How-to-Set-Medication-Reminders-On-Your-Phone-Gear-GettyImages-1337249693.jpg")
                .build();

        var createMedicationRequest = CreateMedicationRequest.builder()
                .name("Medication Test 1")
                .code("MED_TEST_01")
                .weight(120)
                .image("https://media.wired.com/photos/62e426ba69d8da74ff881b70/1:1/w_1800,h_1800,c_limit/How-to-Set-Medication-Reminders-On-Your-Phone-Gear-GettyImages-1337249693.jpg")
                .build();

        when(medicationRepository.save(Mockito.any(Medication.class))).thenReturn(medication);

        var response = medicationService.createMedication(createMedicationRequest);

        var expectedResponse = CustomResponse.builder()
                .message(null)
                .data(medication)
                .build();

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(expectedResponse.toString(), response.getBody().toString());
    }

    @DisplayName("Failed create medication and return error medication name not contain letters / number / - / _")
    @Test
    public void testCreateMedicationAndFailedBecauseMedicationNameNotPassTheValidation() {

        var medication = Medication.builder()
                .name("Medication Test 1*")
                .code("MED_TEST_01")
                .weight(120)
                .image("https://media.wired.com/photos/62e426ba69d8da74ff881b70/1:1/w_1800,h_1800,c_limit/How-to-Set-Medication-Reminders-On-Your-Phone-Gear-GettyImages-1337249693.jpg")
                .build();

        var createMedicationRequest = CreateMedicationRequest.builder()
                .name("Medication Test 1*")
                .code("MED_TEST_01")
                .weight(120)
                .image("https://media.wired.com/photos/62e426ba69d8da74ff881b70/1:1/w_1800,h_1800,c_limit/How-to-Set-Medication-Reminders-On-Your-Phone-Gear-GettyImages-1337249693.jpg")
                .build();

        when(medicationRepository.save(Mockito.any(Medication.class))).thenReturn(medication);

        var response = medicationService.createMedication(createMedicationRequest);

        var expectedResponse = CustomResponse.builder()
                .message("medication name only allow letters, numbers, underscore and dash.")
                .data(null)
                .build();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(expectedResponse.toString(), response.getBody().toString());
    }

    @DisplayName("Failed create medication and return error medication code not contain upper case letters / number / _")
    @Test
    public void testCreateMedicationAndFailedBecauseMedicationCodeNotPassTheValidation() {

        var medication = Medication.builder()
                .name("Medication Test 1")
                .code("MED_TEST_01-")
                .weight(120)
                .image("https://media.wired.com/photos/62e426ba69d8da74ff881b70/1:1/w_1800,h_1800,c_limit/How-to-Set-Medication-Reminders-On-Your-Phone-Gear-GettyImages-1337249693.jpg")
                .build();

        var createMedicationRequest = CreateMedicationRequest.builder()
                .name("Medication Test 1")
                .code("MED_TEST_01-")
                .weight(120)
                .image("https://media.wired.com/photos/62e426ba69d8da74ff881b70/1:1/w_1800,h_1800,c_limit/How-to-Set-Medication-Reminders-On-Your-Phone-Gear-GettyImages-1337249693.jpg")
                .build();

        when(medicationRepository.save(Mockito.any(Medication.class))).thenReturn(medication);

        var response = medicationService.createMedication(createMedicationRequest);

        var expectedResponse = CustomResponse.builder()
                .message("medication code only allow upper case letters, numbers and underscore.")
                .data(null)
                .build();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(expectedResponse.toString(), response.getBody().toString());
    }

}
