package com.works.services;

import com.works.entities.Basket;
import com.works.entities.Customer;
import com.works.entities.Products;
import com.works.repositories.BasketRepository;
import com.works.repositories.ProductRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BasketService {
    final BasketRepository bRepo;
    final ProductRepository pRepo;
    final HttpSession session;

    public BasketService(BasketRepository bRepo, ProductRepository pRepo, HttpSession session) {
        this.bRepo = bRepo;
        this.pRepo = pRepo;
        this.session = session;
    }
    public ResponseEntity<Map<REnum,Object>> save(Basket basket){
        Optional<Products> products=pRepo.findById(basket.getProduct().getId());
        Customer cus= (Customer) session.getAttribute("customer");
        if (products.isPresent()){
            basket.setCustomer(cus);
            basket.setProduct(products.get());
            Basket b=bRepo.save(basket);
            // Products product=new Products();
            // products.get().setProductQuaintity(products.get().getProductQuaintity()- basket.getProductQuaintity());
            Map<REnum, Object> hm=new LinkedHashMap<>();
            hm.put(REnum.status,true);
            hm.put(REnum.result,b);
            return new ResponseEntity<>(hm, HttpStatus.OK);
        }else {
            Map<REnum, Object> hm=new LinkedHashMap<>();
            hm.put(REnum.status,false);
            hm.put(REnum.message,"Invalid Product Id");
            return new ResponseEntity<>(hm, HttpStatus.NOT_ACCEPTABLE);
        }

    }
    public ResponseEntity<Map<REnum,Object>> list(){
        Map<REnum,Object> hm=new LinkedHashMap<>();
        hm.put(REnum.status,true);
        hm.put(REnum.result, bRepo.findAll());



        return new  ResponseEntity(hm, HttpStatus.OK);
    }
    public ResponseEntity<Map<String ,Object>> delete(Long  id ){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        try {
            bRepo.deleteById(id);
            hm.put(REnum.status,true);
            return new  ResponseEntity(hm, HttpStatus.OK);
        }catch (Exception ex) {
            hm.put(REnum.status,false);
            hm.put(REnum.message, ex.getMessage());
            return new  ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<Map<String ,Object>> update(Basket basket){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        try{
            Optional<Basket> oBasket = bRepo.findById(basket.getId());
            if(oBasket.isPresent()){
                bRepo.saveAndFlush(basket);
                hm.put(REnum.result, basket);
                hm.put(REnum.status, true);
            }else{
                hm.put(REnum.status, false);
            }
        }catch (Exception e){
            hm.put(REnum.status, false);
            hm.put(REnum.message, e.getMessage());
        }
        return new  ResponseEntity(hm, HttpStatus.OK);
    }
}
