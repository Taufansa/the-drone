package com.thedrone.app.repositories;

import com.thedrone.app.models.Delivery;
import com.thedrone.app.models.DeliveryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryItemRepository extends JpaRepository<DeliveryItem, Integer> {

    List<DeliveryItem> findAllByDelivery(Delivery delivery);

}
