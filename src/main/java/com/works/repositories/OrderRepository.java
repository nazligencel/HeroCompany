package com.works.repositories;

import com.works.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Orders,Long> {


}
