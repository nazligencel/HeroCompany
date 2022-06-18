package com.works.entities;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    @ManyToOne
    @JoinColumn(name = "customer_Id")
    private Customer customer;

    private Integer productQuaintity;
    private Boolean status=false;
}
