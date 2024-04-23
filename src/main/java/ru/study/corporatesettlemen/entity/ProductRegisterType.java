package ru.study.corporatesettlemen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "tpp_ref_product_register_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRegisterType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internal_id;
    private String value;
    private String register_type_name;
    @ManyToOne
    @JoinColumn(name = "product_class_code", referencedColumnName = "value")
    private ProductClass product_class;
    private Timestamp register_type_start_date;
    private Timestamp register_type_end_date;
    @ManyToOne
    @JoinColumn(name = "account_type", referencedColumnName = "value")
    private AccountType account_type;
}
