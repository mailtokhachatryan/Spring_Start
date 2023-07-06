package am.myOffice.shopJDBC.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String category;
    @Column(name = "isexists")
    private boolean isExists;

    @Override
    public String toString() {
        return "Product |  " +
               "id: " + id +
               ", name: " + name +
               ", category: " + category +
               ", isExists: " + isExists;
    }
}
