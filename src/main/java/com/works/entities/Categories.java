package com.works.entities;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
public class Categories extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank(message = "Category Name Can't Be Blank Please Enter Category Name")
    //@Length(message = "Please Enter Minimum 1 Maximum 50 Characters", min = 1, max = 50)
    private String categoryName;

    /*@OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Products> products;*/



}
