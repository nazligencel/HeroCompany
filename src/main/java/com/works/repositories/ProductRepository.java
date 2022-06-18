package com.works.repositories;
import com.works.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products,Long> {
    List<Products> findByCategory_IdEquals(Long id);
    @Query(value ="select product_name as productname, detail as productdetail from products where product_name LIKE '%?1%' or detail LIKE '%?1%'",nativeQuery =  true)
    List<Products> searchProduct(String data);
}
