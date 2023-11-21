package com.thedrone.app.services;

import com.thedrone.app.dtos.CreateDeliveryRequest;
import com.thedrone.app.dtos.CustomResponse;
import com.thedrone.app.dtos.DeliveryItemRequest;
import com.thedrone.app.enums.Constant;
import com.thedrone.app.models.Delivery;
import com.thedrone.app.models.DeliveryItem;
import com.thedrone.app.models.Drone;
import com.thedrone.app.models.Medication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeliveryServiceTest extends BaseServiceTest{

    @InjectMocks
    private DeliveryService deliveryService;

    private Drone drone;
    private Delivery delivery;
    private Medication medication1;
    private Medication medication2;
    private Integer medicationId1;
    private Integer medicationId2;
    private Integer deliveryId;
    private Integer droneId;
    private String serialNumber;
    private DeliveryItem deliveryItem1;
    private DeliveryItem deliveryItem2;

    private DeliveryItemRequest deliveryItemRequest1;
    private DeliveryItemRequest deliveryItemRequest2;

    @BeforeEach
    public void setup() {
        deliveryId = 1;
        droneId = 1;
        medicationId1 = 1;
        medicationId2 = 2;
        serialNumber = UUID.randomUUID().toString();

        drone = Drone.builder()
                .id(droneId)
                .name("Drone Test 1")
                .model(Constant.DroneModel.Middleweight.toString())
                .battery(100)
                .weightCapability(600)
                .serialNumber(serialNumber)
                .state(Constant.DroneState.IDLE.toString())
                .build();

        delivery = Delivery.builder()
                .id(deliveryId)
                .drone(drone)
                .totalWeight(240)
                .build();

        medication1 = Medication.builder()
                .id(medicationId1)
                .name("Medication Test 1")
                .code("MT01")
                .weight(100)
                .image("https://media.wired.com/photos/62e426ba69d8da74ff881b70/1:1/w_1800,h_1800,c_limit/How-to-Set-Medication-Reminders-On-Your-Phone-Gear-GettyImages-1337249693.jpg")
                .build();

        medication2 = Medication.builder()
                .id(medicationId2)
                .name("Medication Test 2")
                .code("MT02")
                .weight(140)
                .image("https://media.wired.com/photos/62e426ba69d8da74ff881b70/1:1/w_1800,h_1800,c_limit/How-to-Set-Medication-Reminders-On-Your-Phone-Gear-GettyImages-1337249693.jpg")
                .build();

        deliveryItem1 = DeliveryItem.builder()
                .id(1)
                .delivery(delivery)
                .medication(medication1)
                .quantity(1)
                .build();

        deliveryItem2 = DeliveryItem.builder()
                .id(2)
                .delivery(delivery)
                .medication(medication2)
                .quantity(1)
                .build();

        deliveryItemRequest1 = DeliveryItemRequest.builder()
                .medicationId(medicationId1)
                .quantity(1)
                .build();

        deliveryItemRequest2 = DeliveryItemRequest.builder()
                .medicationId(medicationId2)
                .quantity(1)
                .build();
    }

    @DisplayName("Success to fetch delivery items by delivery id")
    @Test
    public void testSuccessToFetchDeliveryItemsByDeliveryId() {

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.ofNullable(delivery));
        when(deliveryItemRepository.findAllByDelivery(delivery)).thenReturn(List.of(deliveryItem1, deliveryItem2));

        var response = deliveryService.getDeliveryItems(deliveryId);

        var expectedResponse = CustomResponse.builder()
                .message(null)
                .data(List.of(deliveryItem1, deliveryItem2))
                .build();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());

    }

    @DisplayName("Failed to fetch delivery items by delivery id because delivery not found")
    @Test
    public void testFailedToFetchDeliveryItemsBecauseDeliveryNotFound() {

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.ofNullable(null));

        var response = deliveryService.getDeliveryItems(deliveryId);

        var expectedResponse = CustomResponse.builder()
                .message("no delivery found.")
                .data(null)
                .build();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(expectedResponse.toString(), response.getBody().toString());
    }

    @DisplayName("Success to create delivery")
    @Test
    public void testSuccessCreateDelivery() {
        var createDeliveryRequest = CreateDeliveryRequest.builder()
                .droneId(droneId)
                .deliveryItemRequests(Set.of(deliveryItemRequest1, deliveryItemRequest2))
                .build();

        when(droneRepository.findById(createDeliveryRequest.getDroneId())).thenReturn(Optional.ofNullable(drone));
        when(deliveryRepository.save(Mockito.any(Delivery.class))).thenReturn(delivery);
        when(medicationRepository.findAllById(Mockito.any(List.class))).thenReturn(List.of(medication1, medication2));

        var response = deliveryService.createDelivery(createDeliveryRequest);

        var expectedResponse = CustomResponse.builder()
                .message("new delivery in process.")
                .data(delivery)
                .build();

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @DisplayName("Failed to create delivery because drone not found")
    @Test
    public void testFailedCreateDeliveryBecauseNoDroneFound() {
        var createDeliveryRequest = CreateDeliveryRequest.builder()
                .droneId(droneId)
                .deliveryItemRequests(Set.of(deliveryItemRequest1, deliveryItemRequest2))
                .build();

        when(droneRepository.findById(createDeliveryRequest.getDroneId())).thenReturn(Optional.ofNullable(null));
        when(deliveryRepository.save(Mockito.any(Delivery.class))).thenReturn(delivery);
        when(medicationRepository.findAllById(Mockito.any(List.class))).thenReturn(List.of(medication1, medication2));

        var response = deliveryService.createDelivery(createDeliveryRequest);

        var expectedResponse = CustomResponse.builder()
                .message("no drone found.")
                .data(null)
                .build();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @DisplayName("Failed to create delivery because drone state is unavailable")
    @Test
    public void testFailedCreateDeliveryBecauseDroneStateUnavailable() {
        drone.setState(Constant.DroneState.DELIVERING.toString());

        var createDeliveryRequest = CreateDeliveryRequest.builder()
                .droneId(droneId)
                .deliveryItemRequests(Set.of(deliveryItemRequest1, deliveryItemRequest2))
                .build();

        when(droneRepository.findById(createDeliveryRequest.getDroneId())).thenReturn(Optional.ofNullable(drone));
        when(deliveryRepository.save(Mockito.any(Delivery.class))).thenReturn(delivery);
        when(medicationRepository.findAllById(Mockito.any(List.class))).thenReturn(List.of(medication1, medication2));

        var response = deliveryService.createDelivery(createDeliveryRequest);

        var expectedResponse = CustomResponse.builder()
                .message("drone unavailable.")
                .data(null)
                .build();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @DisplayName("Failed to create delivery because drone battery is below 25%")
    @Test
    public void testFailedCreateDeliveryBecauseDroneBatteryIsLow() {
        drone.setBattery(20);

        var createDeliveryRequest = CreateDeliveryRequest.builder()
                .droneId(droneId)
                .deliveryItemRequests(Set.of(deliveryItemRequest1, deliveryItemRequest2))
                .build();

        when(droneRepository.findById(createDeliveryRequest.getDroneId())).thenReturn(Optional.ofNullable(drone));
        when(deliveryRepository.save(Mockito.any(Delivery.class))).thenReturn(delivery);
        when(medicationRepository.findAllById(Mockito.any(List.class))).thenReturn(List.of(medication1, medication2));

        var response = deliveryService.createDelivery(createDeliveryRequest);

        var expectedResponse = CustomResponse.builder()
                .message("drone battery below 25%")
                .data(null)
                .build();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @DisplayName("Failed to create delivery because total weight of items cannot more than 500mg")
    @Test
    public void testFailedCreateDeliveryBecauseTotalWeightCannotMoreThan500Mg() {
        deliveryItemRequest1.setQuantity(4);
        deliveryItemRequest2.setQuantity(4);

        var createDeliveryRequest = CreateDeliveryRequest.builder()
                .droneId(droneId)
                .deliveryItemRequests(Set.of(deliveryItemRequest1, deliveryItemRequest2))
                .build();

        when(droneRepository.findById(createDeliveryRequest.getDroneId())).thenReturn(Optional.ofNullable(drone));
        when(deliveryRepository.save(Mockito.any(Delivery.class))).thenReturn(delivery);
        when(medicationRepository.findAllById(Mockito.any(List.class))).thenReturn(List.of(medication1, medication2));

        var response = deliveryService.createDelivery(createDeliveryRequest);

        var expectedResponse = CustomResponse.builder()
                .message("total weight of the items is cannot more than 500mg.")
                .data(null)
                .build();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @DisplayName("Failed to create delivery because total weight of items cannot more than drone weight capacity")
    @Test
    public void testFailedCreateDeliveryBecauseTotalWeightCannotMoreThanWeightCapacityInDrone() {
        drone.setWeightCapability(400);
        deliveryItemRequest1.setQuantity(3);

        var createDeliveryRequest = CreateDeliveryRequest.builder()
                .droneId(droneId)
                .deliveryItemRequests(Set.of(deliveryItemRequest1, deliveryItemRequest2))
                .build();

        when(droneRepository.findById(createDeliveryRequest.getDroneId())).thenReturn(Optional.ofNullable(drone));
        when(deliveryRepository.save(Mockito.any(Delivery.class))).thenReturn(delivery);
        when(medicationRepository.findAllById(Mockito.any(List.class))).thenReturn(List.of(medication1, medication2));

        var response = deliveryService.createDelivery(createDeliveryRequest);

        var expectedResponse = CustomResponse.builder()
                .message("the drone cannot to carry items because total weight of the items exceed the drone weight capability.")
                .data(null)
                .build();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }
}
