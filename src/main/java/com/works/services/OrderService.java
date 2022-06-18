package com.works.services;

import com.works.entities.*;
import com.works.repositories.BasketRepository;
import com.works.repositories.OrderRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    final OrderRepository oRepo;
    final BasketRepository bRepo;
    final HttpSession session;

    public OrderService(OrderRepository oRepo, BasketRepository bRepo, HttpSession session) {
        this.oRepo = oRepo;
        this.bRepo = bRepo;
        this.session = session;
    }

    public ResponseEntity<Map<REnum,Object>> list(){
        Map<REnum,Object> hm=new LinkedHashMap<>();
        hm.put(REnum.status,true);
        hm.put(REnum.result, oRepo.findAll());

        return new  ResponseEntity(hm, HttpStatus.OK);
    }

    public ResponseEntity<Map<String,Object>> add(){
         Map<REnum, Object> hm = new LinkedHashMap<>();
    Orders orders=new Orders();
    int sum=0;
    Customer customer= (Customer) session.getAttribute("customer");
        if(customer!=null) {
        List<Basket> baskets=bRepo.findByCustomer_EmailEqualsIgnoreCaseAndStatusIsFalse(customer.getEmail());
        if (baskets.size() > 0) {
            orders.setCustomer(customer);
            orders.setBaskets(baskets);
            for (Basket item : baskets) {
                sum = sum + item.getProduct().getPrice();
                item.setStatus(true);
                bRepo.saveAndFlush(item);
            }
            orders.setTotal(sum);
            oRepo.save(orders);
            hm.put(REnum.status, true);
            hm.put(REnum.result, orders);
            return new ResponseEntity(hm, HttpStatus.OK);
        } else {
            hm.put(REnum.status, false);
            hm.put(REnum.message, "Basket is empty");
            return new ResponseEntity(hm, HttpStatus.NOT_ACCEPTABLE);
        }
    }else {
        hm.put(REnum.status, false);
        hm.put(REnum.message, "No such a customer ");
        return new ResponseEntity(hm, HttpStatus.NOT_ACCEPTABLE);
    }
    }


}
