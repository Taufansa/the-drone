package com.thedrone.app.services;

import com.thedrone.app.dtos.CreateDroneRequest;
import com.thedrone.app.dtos.CustomResponse;
import com.thedrone.app.enums.Constant;
import com.thedrone.app.models.Drone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DroneServiceTest extends BaseServiceTest {

    @InjectMocks
    private DroneService droneService;

    @DisplayName("Success to create drone and return the created record")
    @Test
    public void testCreateDroneAndSuccess() {
        var serialNumber = UUID.randomUUID().toString();

        var drone = Drone.builder()
                .id(1)
                .name("Drone Test 1")
                .model(Constant.DroneModel.Middleweight.toString())
                .battery(100)
                .serialNumber(serialNumber)
                .state(Constant.DroneState.IDLE.toString())
                .build();

        var createDroneRequest = CreateDroneRequest.builder()
                .serialNumber(serialNumber)
                .name("Drone Test 1")
                .model(Constant.DroneModel.Middleweight.toString())
                .weightCapability(700)
                .build();

        when(droneRepository.save(Mockito.any(Drone.class))).thenReturn(drone);

        var response = droneService.createDrone(createDroneRequest);

        var expectedResponse = CustomResponse.builder()
                .message(null)
                .data(drone)
                .build();

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(expectedResponse.toString(), response.getBody().toString());
    }

    @DisplayName("Failed to create drone and return error serial number used")
    @Test
    public void testCreateDroneAndFailedBecauseSerialNumberUsed() {
        var serialNumber = UUID.randomUUID().toString();

        var drone = Drone.builder()
                .id(1)
                .name("Drone Test 1")
                .model(Constant.DroneModel.Middleweight.toString())
                .battery(100)
                .serialNumber(serialNumber)
                .state(Constant.DroneState.IDLE.toString())
                .build();

        var createDroneRequest = CreateDroneRequest.builder()
                .serialNumber(serialNumber)
                .name("Drone Test 1")
                .model(Constant.DroneModel.Middleweight.toString())
                .weightCapability(700)
                .build();

        when(droneRepository.findFirstBySerialNumber(serialNumber)).thenReturn(drone);

        when(droneRepository.save(Mockito.any(Drone.class))).thenReturn(drone);

        var response = droneService.createDrone(createDroneRequest);

        var expectedResponse = CustomResponse.builder()
                .message("serial number already used by other drone.")
                .data(null)
                .build();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(expectedResponse.toString(), response.getBody().toString());
    }

    @DisplayName("Failed to create drone and return error")
    @Test
    public void testCreateDroneAndFailedBecauseSerialNumberLengthMoreThan100Chars() {
        var serialNumber = UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString();

        var drone = Drone.builder()
                .id(1)
                .name("Drone Test 1")
                .model(Constant.DroneModel.Middleweight.toString())
                .battery(100)
                .serialNumber(serialNumber)
                .state(Constant.DroneState.IDLE.toString())
                .build();

        var createDroneRequest = CreateDroneRequest.builder()
                .serialNumber(serialNumber)
                .name("Drone Test 1")
                .model(Constant.DroneModel.Middleweight.toString())
                .weightCapability(700)
                .build();

        when(droneRepository.save(Mockito.any(Drone.class))).thenReturn(drone);

        var response = droneService.createDrone(createDroneRequest);

        var expectedResponse = CustomResponse.builder()
                .message("drone serial number can not more than 100 characters.")
                .data(null)
                .build();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(expectedResponse.toString(), response.getBody().toString());
    }

    @DisplayName("Success to fetch drone by id")
    @Test
    public void testFetchDroneByIdAndSuccess() {
        var serialNumber = UUID.randomUUID().toString();
        var droneid = 1;

        var drone = Drone.builder()
                .id(droneid)
                .name("Drone Test 1")
                .model(Constant.DroneModel.Middleweight.toString())
                .battery(100)
                .serialNumber(serialNumber)
                .state(Constant.DroneState.IDLE.toString())
                .build();

        when(droneRepository.findById(droneid)).thenReturn(Optional.ofNullable(drone));

        var response = droneService.detailDrone(droneid);

        var expectedResponse = CustomResponse.builder()
                .message(null)
                .data(drone)
                .build();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedResponse.toString(), response.getBody().toString());
    }

    @DisplayName("Failed to fetch drone by id and return error no drone found")
    @Test
    public void testFetchDroneByIdAndFailedBecauseDroneNotFound() {
        var serialNumber = UUID.randomUUID().toString();
        var droneid = 1;

        var drone = Drone.builder()
                .id(droneid)
                .name("Drone Test 1")
                .model(Constant.DroneModel.Middleweight.toString())
                .battery(100)
                .serialNumber(serialNumber)
                .state(Constant.DroneState.IDLE.toString())
                .build();

        when(droneRepository.findById(droneid)).thenReturn(Optional.ofNullable(null));

        var response = droneService.detailDrone(droneid);

        var expectedResponse = CustomResponse.builder()
                .message("no drone found.")
                .data(null)
                .build();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(expectedResponse.toString(), response.getBody().toString());
    }

    @DisplayName("Success to fetch all drone by state and return records")
    @Test
    public void testFetchAllDroneByStateAndSuccess() {
        var drone1 = Drone.builder()
                .id(1)
                .name("Drone Test 1")
                .model(Constant.DroneModel.Middleweight.toString())
                .battery(100)
                .serialNumber(UUID.randomUUID().toString())
                .state(Constant.DroneState.IDLE.toString())
                .build();

        var drone2 = Drone.builder()
                .id(2)
                .name("Drone Test 2")
                .model(Constant.DroneModel.Cruiserweight.toString())
                .battery(100)
                .serialNumber(UUID.randomUUID().toString())
                .state(Constant.DroneState.IDLE.toString())
                .build();

        when(droneRepository.findAllByState(Constant.DroneState.IDLE.toString())).thenReturn(List.of(drone1, drone2));

        var response = droneService.availableDrones();

        var expectedResponse = CustomResponse.builder()
                .message(null)
                .data(List.of(drone1, drone2))
                .build();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedResponse.toString(), response.getBody().toString());
    }

    @DisplayName("Success to fetch all drone by state but return no data")
    @Test
    public void testFetchAllDroneByStateAndSuccessButReturnNoData() {
        var drone1 = Drone.builder()
                .id(1)
                .name("Drone Test 1")
                .model(Constant.DroneModel.Middleweight.toString())
                .battery(100)
                .serialNumber(UUID.randomUUID().toString())
                .state(Constant.DroneState.LOADED.toString())
                .build();

        var drone2 = Drone.builder()
                .id(2)
                .name("Drone Test 2")
                .model(Constant.DroneModel.Cruiserweight.toString())
                .battery(100)
                .serialNumber(UUID.randomUUID().toString())
                .state(Constant.DroneState.DELIVERING.toString())
                .build();

        when(droneRepository.findAllByState(Constant.DroneState.IDLE.toString())).thenReturn(null);

        var response = droneService.availableDrones();

        var expectedResponse = CustomResponse.builder()
                .message(null)
                .data(null)
                .build();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedResponse.toString(), response.getBody().toString());
    }

}
