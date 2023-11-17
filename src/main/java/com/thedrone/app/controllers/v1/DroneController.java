package com.thedrone.app.controllers.v1;

import com.thedrone.app.controllers.BaseController;
import com.thedrone.app.dtos.CreateDroneRequest;
import com.thedrone.app.dtos.CustomResponse;
import com.thedrone.app.enums.Constant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.basePathV1 + "/drone")
public class DroneController extends BaseController {

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> saveNewDrone(@RequestBody CreateDroneRequest createDroneRequest) {
        return droneService.createDrone(createDroneRequest);
    }

    @GetMapping("/{droneId}")
    public ResponseEntity<CustomResponse> getDroneDetails(@PathVariable("droneId") Integer droneId) {
        return droneService.detailDrone(droneId);
    }

    @GetMapping("/available")
    public ResponseEntity<CustomResponse> getAvailableDrones() {
        return droneService.availableDrones();
    }
}
