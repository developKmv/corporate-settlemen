package ru.study.corporatesettlemen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.IdGeneratorType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "tpp_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigInteger product_code_id;
    private BigInteger client_id;
    private String type;
    private String number;
    private BigInteger priority;
    private Timestamp date_of_conclusion;
    private Timestamp start_date_time;
    private Timestamp end_date_time;
    private BigInteger days;
    private BigDecimal penalty_rate;
    private BigDecimal nso;
    private BigDecimal threshold_amount;
    private String requisite_type;
    private String interest_rate_type;
    private BigDecimal tax_rate;
    private String reasone_close;
    private String state;
}
