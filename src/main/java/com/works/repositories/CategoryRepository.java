package com.works.repositories;

import com.works.entities.Categories;
import com.works.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Categories,Long> {
   // @Query(value = "select  p.cat_id as CatId,c.category_name as CategoryName,p.product_name as ProdcutName, p.product_details as Detay, p.price from product as p inner join  categories as c  on c.cat_id = p.cat_id", nativeQuery = true)
   // List<Categories> catJoin();

}
