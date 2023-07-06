package am.myOffice.shopJDBC.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userid")
    private Long userId;
    @Column(name = "productid")
    private Long productId;
    @Column(name = "totalprice")
    private double totalPrice;
    @Column(name = "totalcountofproduct")
    private int totalCountOfProduct;

    public Order(Long userId, Long productId, double totalPrice, int totalCountOfProduct) {
        this.userId = userId;
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.totalCountOfProduct = totalCountOfProduct;
    }


}