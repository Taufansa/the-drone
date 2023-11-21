package com.thedrone.app.services;

import com.thedrone.app.dtos.CreateDeliveryRequest;
import com.thedrone.app.dtos.CustomResponse;
import com.thedrone.app.enums.Constant;
import com.thedrone.app.models.Delivery;
import com.thedrone.app.models.DeliveryItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class DeliveryService extends BaseService {

    public ResponseEntity<CustomResponse> getDeliveryItems(Integer deliveryId) {
        try {
            var response = new CustomResponse();
            var delivery = deliveryRepository.findById(deliveryId);

            if (delivery.isEmpty()) {
                response.setMessage("no delivery found.");
                response.setData(null);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            var deliveryItems = deliveryItemRepository.findAllByDelivery(delivery.get());

            delivery.get().setDeliveryItems(deliveryItems);

            response.setMessage(null);
            response.setData(delivery.get().getDeliveryItems());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            var response = new CustomResponse();
            response.setMessage(e.getMessage());
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<CustomResponse> createDelivery(CreateDeliveryRequest createDeliveryRequest) {
        try {
            var response = new CustomResponse();

            var drone = droneRepository.findById(createDeliveryRequest.getDroneId());

            if (drone.isEmpty()) {
                response.setMessage("no drone found.");
                response.setData(null);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (!drone.get().getState().equals(Constant.DroneState.IDLE.toString())) {
                response.setMessage("drone unavailable.");
                response.setData(null);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (drone.get().getBattery() < 25) {
                response.setMessage("drone battery below 25%");
                response.setData(null);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            var medicationIds = new ArrayList<Integer>();

            createDeliveryRequest.getDeliveryItemRequests().stream().map(x -> {
                medicationIds.add(x.getMedicationId());
                return x;
            }).collect(Collectors.toList());

            var medications = medicationRepository.findAllById(medicationIds);

            var deliveryItems = new ArrayList<DeliveryItem>();

            createDeliveryRequest.getDeliveryItemRequests().stream().map(x -> {
                deliveryItems.add(
                        DeliveryItem.builder()
                                .quantity(x.getQuantity())
                                .medication(medications.stream().filter(
                                        y -> y.getId().equals(x.getMedicationId())
                                    ).findFirst().get())
                                .build()
                );
                return x;
            }).collect(Collectors.toList());

            AtomicInteger totalWeight = new AtomicInteger();

            deliveryItems.stream().map(x -> {
                totalWeight.addAndGet(x.getQuantity() * x.getMedication().getWeight());
                return x;
            }).collect(Collectors.toList());

            if (totalWeight.get() > Constant.maxWeight) {
                response.setMessage("total weight of the items is cannot more than 500mg.");
                response.setData(null);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (totalWeight.get() > drone.get().getWeightCapability()) {
                response.setMessage("the drone cannot to carry items because total weight of the items exceed the drone weight capability.");
                response.setData(null);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            var newDelivery = Delivery.builder()
                        .drone(drone.get())
                        .totalWeight(totalWeight.get())
                    .build();

            var result = deliveryRepository.save(newDelivery);

            AtomicInteger i = new AtomicInteger();
            deliveryItems.stream().map(x -> {
                deliveryItems.get(i.get()).setDelivery(result);
                i.getAndIncrement();

                return x;
            }).collect(Collectors.toList());

            deliveryItemRepository.saveAll(deliveryItems);

            response.setMessage("new delivery in process.");
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
