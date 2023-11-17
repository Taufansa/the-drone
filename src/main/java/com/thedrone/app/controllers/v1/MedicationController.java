package com.thedrone.app.controllers.v1;

import com.thedrone.app.controllers.BaseController;
import com.thedrone.app.dtos.CreateMedicationRequest;
import com.thedrone.app.dtos.CustomResponse;
import com.thedrone.app.enums.Constant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.basePathV1 + "/medication")
public class MedicationController extends BaseController {

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> saveMedication(@RequestBody CreateMedicationRequest createMedicationRequest) {
        return medicationService.createMedication(createMedicationRequest);
    }

}
