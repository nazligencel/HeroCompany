package com.works.restcontrollers;

import com.works.entities.Categories;
import com.works.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("categories")
public class CategoryRestController {
    final CategoryService catService;


    public CategoryRestController(CategoryService catService) {
        this.catService = catService;
    }
    @PostMapping("/save")
    public ResponseEntity save(@Valid @RequestBody Categories cat){
        return catService.save(cat);
    }
    @PutMapping("/update")
    public ResponseEntity update(@Valid @RequestBody Categories cat){
        return catService.update(cat);
    }
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam Long catid){
        return catService.delete(catid);
    }
    @GetMapping("/list")
    public ResponseEntity list(){
        return catService.list();
    }
}


