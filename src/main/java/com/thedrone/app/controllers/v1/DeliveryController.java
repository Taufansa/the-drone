package com.thedrone.app.controllers.v1;

import com.thedrone.app.controllers.BaseController;
import com.thedrone.app.dtos.CreateDeliveryRequest;
import com.thedrone.app.dtos.CustomResponse;
import com.thedrone.app.enums.Constant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.basePathV1 + "/delivery")
public class DeliveryController extends BaseController {

    @GetMapping("/items/{deliveryId}")
    public ResponseEntity<CustomResponse> getLoadedMedications(@PathVariable("deliveryId") Integer deliveryId) {
        return deliveryService.getDeliveryItems(deliveryId);
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> saveDelivery(@RequestBody CreateDeliveryRequest createDeliveryRequest) {
        return deliveryService.createDelivery(createDeliveryRequest);
    }
}
