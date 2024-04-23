package ru.study.corporatesettlemen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tpp_ref_product_class")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internal_id;
/*    @OneToMany(mappedBy = "product_class")
    private List<ProductRegisterType> value;*/
    private String value;
    private String gbi_code;
    private String gbi_name;
    private String product_row_code;
    private String product_row_name;
    private String subclass_code;
    private String subclass_name;
}
