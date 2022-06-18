package com.works.entities;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;



@Entity
@Data
public class Products extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product Name can't be blank")
    @Length(message = "Product Name must contain min 2 max  50 character.", min = 2, max = 50)
    private String productName;


    @Length(message = "Company name must contain min 2 max  50 character.", min = 2, max = 50)
    private String detail;
    private Integer productQuaintity;
    private int price;

    @ManyToOne

    @JoinColumn(name="category_id",referencedColumnName = "id")
    private Categories category;

}