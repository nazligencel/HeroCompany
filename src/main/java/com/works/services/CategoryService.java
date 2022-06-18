package com.works.services;

import com.works.entities.Categories;
import com.works.entities.Customer;
import com.works.repositories.CategoryRepository;
import com.works.repositories.CustomerRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.works.utils.REnum.jwt;

@Service
public class CategoryService {
    final CategoryRepository catRepo;



    public CategoryService(CategoryRepository catRepo) {
        this.catRepo = catRepo;
    }

    public ResponseEntity<Map<REnum, Object>> save(Categories categories) {
        Categories cat=catRepo.save(categories);
        Map<REnum, Object> hm = new LinkedHashMap<>();
        hm.put(REnum.status, true);
        hm.put(REnum.result, cat);
        return new ResponseEntity<>(hm, HttpStatus.OK);
    }
    public ResponseEntity<Map<REnum,Object>> list(){
        Map<REnum,Object> hm=new LinkedHashMap<>();
        hm.put(REnum.status,true);
        hm.put(REnum.result, catRepo.findAll());



        return new  ResponseEntity(hm, HttpStatus.OK);
    }

    public ResponseEntity<Map<String ,Object>> delete(Long  catid ){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        try {
            catRepo.deleteById(catid);
            hm.put(REnum.status,true);
            return new  ResponseEntity(hm, HttpStatus.OK);
        }catch (Exception ex) {
            hm.put(REnum.status,false);
            hm.put(REnum.message, ex.getMessage());
            return new  ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<Map<String ,Object>> update(Categories categories){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        try{
            Optional<Categories> oCategories = catRepo.findById(categories.getId());
            if(oCategories.isPresent()){
                catRepo.saveAndFlush(categories);
                hm.put(REnum.result, categories);
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
