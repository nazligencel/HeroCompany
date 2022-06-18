package com.works.entities;
import lombok.Data;
import javax.persistence.*;
import java.util.List;


@Entity
@Data
public class Orders extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;


    @OneToMany(cascade = {CascadeType.ALL})

    @JoinTable(name = "Order_Basket",joinColumns = @JoinColumn(name = "order_Id"),
            inverseJoinColumns = @JoinColumn(name = "basket_id",referencedColumnName = "id"))
    private List<Basket> baskets;

    private int total;
}
