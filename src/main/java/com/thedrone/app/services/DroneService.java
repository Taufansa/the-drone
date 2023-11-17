package com.thedrone.app.services;

import com.thedrone.app.dtos.CreateDroneRequest;
import com.thedrone.app.dtos.CustomResponse;
import com.thedrone.app.enums.Constant;
import com.thedrone.app.models.Drone;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DroneService extends BaseService {

    @Transactional
    public void initRecordDrone(){
        try {
            var drones = new ArrayList<Drone>();

            drones.add(Drone.builder()
                    .name("Drone 1")
                    .model(Constant.DroneModel.Middleweight.toString())
                    .serialNumber(UUID.randomUUID().toString())
                    .weightCapability(280)
                    .battery(100)
                    .state(Constant.DroneState.IDLE.toString())
                    .build());
            drones.add(Drone.builder()
                    .name("Drone 2")
                    .model(Constant.DroneModel.Cruiserweight.toString())
                    .serialNumber(UUID.randomUUID().toString())
                    .weightCapability(480)
                    .battery(100)
                    .state(Constant.DroneState.IDLE.toString())
                    .build());
            drones.add(Drone.builder()
                    .name("Drone 3")
                    .model(Constant.DroneModel.Heavyweight.toString())
                    .serialNumber(UUID.randomUUID().toString())
                    .weightCapability(600)
                    .battery(100)
                    .state(Constant.DroneState.IDLE.toString())
                    .build());
            drones.add(Drone.builder()
                    .name("Drone 4")
                    .model(Constant.DroneModel.Lightweight.toString())
                    .serialNumber(UUID.randomUUID().toString())
                    .weightCapability(180)
                    .battery(100)
                    .state(Constant.DroneState.IDLE.toString())
                    .build());
            drones.add(Drone.builder()
                    .name("Drone 5")
                    .model(Constant.DroneModel.Middleweight.toString())
                    .serialNumber(UUID.randomUUID().toString())
                    .weightCapability(250)
                    .battery(100)
                    .state(Constant.DroneState.IDLE.toString())
                    .build());

            droneRepository.saveAll(drones);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public ResponseEntity<CustomResponse> createDrone(CreateDroneRequest createDroneRequest) {
        try {
            var response = new CustomResponse();

            if (Objects.nonNull(droneRepository.findFirstBySerialNumber(createDroneRequest.getSerialNumber()))) {
                response.setMessage("serial number already used by other drone.");
                response.setData(null);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (createDroneRequest.getSerialNumber().length() > 100) {
                response.setMessage("drone serial number can not more than 100 characters.");
                response.setData(null);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            var newDrone = Drone.builder()
                    .model(Constant.DroneModel.valueOf(createDroneRequest.getModel()).toString())
                    .name(createDroneRequest.getName())
                    .weightCapability(createDroneRequest.getWeightCapability())
                    .battery(100)
                    .state(Constant.DroneState.IDLE.toString())
                    .build();

            var result = droneRepository.save(newDrone);

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

    public ResponseEntity<CustomResponse> detailDrone(Integer droneId) {
        try {
            var response = new CustomResponse();

            var drone = droneRepository.findById(droneId);

            if (drone.isEmpty()) {
                response.setMessage("no drone found.");
                response.setData(null);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            response.setMessage(null);
            response.setData(drone.get());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            var response = new CustomResponse();
            response.setMessage(e.getMessage());
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CustomResponse> availableDrones() {
        try {
            var response = new CustomResponse();

            var availableDrones = droneRepository.findAllByState(Constant.DroneState.IDLE.toString());

            response.setMessage(null);
            response.setData(availableDrones);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            var response = new CustomResponse();
            response.setMessage(e.getMessage());
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
