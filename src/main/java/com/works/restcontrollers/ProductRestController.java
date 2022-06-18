package com.works.restcontrollers;

import com.works.entities.Products;
import com.works.services.ProductService;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("product")
public class ProductRestController {
    final ProductService pService;

    public ProductRestController(ProductService pService) {
        this.pService = pService;
    }

    @PostMapping("/save")
    public ResponseEntity save(@Valid @RequestBody Products product) {
        return pService.save(product);
    }

    @PutMapping("/update")
    public ResponseEntity update(@Valid @RequestBody Products product) {
        return pService.update(product);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam Long pid) {
        return pService.delete(pid);
    }

    @GetMapping("/list")
    public ResponseEntity list(Long id) {
        return pService.list(id);
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam @Length(message = "You Must Enter a Minimum of 3 Characters to Search.", min = 3) String data) {
        return pService.search(data);

    }
}
