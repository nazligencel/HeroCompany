package com.works.services;
import com.works.entities.Categories;
import com.works.entities.Products;
import com.works.repositories.CategoryRepository;
import com.works.repositories.ProductRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    final ProductRepository pRepo;
    final CategoryRepository catRepo;



    public ProductService(ProductRepository pRepo, CategoryRepository catRepo) {
        this.pRepo = pRepo;
        this.catRepo = catRepo;
    }
    public ResponseEntity<Map<REnum, Object>> save(Products product) {
        Map<REnum, Object> hm = new LinkedHashMap<>();

        Optional<Categories> optionalCategories=catRepo.findById(product.getCategory().getId());
        if (!optionalCategories.isPresent()){
            hm.put(REnum.status,false);
            hm.put(REnum.message,"Category Not Found");
            return new ResponseEntity<>(hm,HttpStatus.NOT_ACCEPTABLE);

        }else {
            product.setCategory(optionalCategories.get());
            Products p = pRepo.save(product);
            hm.put(REnum.status, true);
            hm.put(REnum.result, p);
            return new ResponseEntity<>(hm, HttpStatus.OK);
        }

    }
    public ResponseEntity<Map<REnum,Object>> list(Long id){
        Map<REnum,Object> hm=new LinkedHashMap<>();
        try {
            pRepo.findByCategory_IdEquals(id);
        }catch (Exception ex){
            hm.put(REnum.status,false);
            hm.put(REnum.message, ex.getMessage());
            return new  ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }
        hm.put(REnum.status,true);
        hm.put(REnum.result, pRepo.findAll());


        return new  ResponseEntity(hm, HttpStatus.OK);
    }
    public ResponseEntity<Map<String ,Object>> delete(Long  pid ){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        try {
            pRepo.deleteById(pid);
            hm.put(REnum.status,true);
            return new  ResponseEntity(hm, HttpStatus.OK);
        }catch (Exception ex) {
            hm.put(REnum.status,false);
            hm.put(REnum.message, ex.getMessage());
            return new  ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<Map<String ,Object>> update(Products product){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        try{
            Optional<Products> oProduct = pRepo.findById(product.getId());
            if(oProduct.isPresent()){
                pRepo.saveAndFlush(product);
                hm.put(REnum.result, product);
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
    public ResponseEntity<Map<REnum,Object>> search(String data) {
        Map<REnum,Object> hm=new LinkedHashMap<>();
        hm.put(REnum.result, pRepo.searchProduct(data));
        return new ResponseEntity<>(hm,HttpStatus.OK);
    }

}
