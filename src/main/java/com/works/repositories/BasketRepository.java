package com.works.repositories;

import com.works.entities.Basket;
import com.works.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {


    List<Basket> findByCustomer_EmailEqualsIgnoreCaseAndStatusIsFalse(String email);



}