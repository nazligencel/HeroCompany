package com.works.restcontrollers;

import com.works.entities.Basket;
import com.works.services.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/basket")
public class BasketRestController {
    final BasketService bService;

    public BasketRestController(BasketService bService) {
        this.bService = bService;
    }
    @PostMapping("/save")
    public ResponseEntity save(@Valid @RequestBody Basket basket){
        return bService.save(basket);
    }
    @GetMapping("/update")
    public ResponseEntity update(@Valid @RequestBody Basket basket){
        return  bService.update(basket);
    }
    @DeleteMapping("delete")
    public ResponseEntity delete(@RequestParam Long id){
        return bService.delete(id);
    }
    @GetMapping("/list")
    public ResponseEntity list(){
        return bService.list();
    }

}
