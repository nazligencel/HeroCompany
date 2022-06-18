package com.works.restcontrollers;


import com.works.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("order")
public class OrderRestController {
    final OrderService orderService;


    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/list")
    public ResponseEntity list(){
        return orderService.list();
    }
    @PostMapping("/add")
    public ResponseEntity add(){
        return orderService.add();
    }

}
