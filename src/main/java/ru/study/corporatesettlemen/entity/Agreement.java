package ru.study.corporatesettlemen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product_id;
    private String general_agreement_id;
    private String supplementary_agreement_id;
    private String arrangement_type;
    private BigInteger sheduler_job_id;
    private String number;
    private Timestamp opening_date;
    private Timestamp closing_date;
    private Timestamp cancel_date;
    private BigInteger validity_duration;
    private String cancellation_reason;
    private String status;
    private Timestamp interest_calculation_date;
    private Float interest_rate;
    private Float coefficient;
    private String coefficient_action;
    private Float minimum_interest_rate;
    private BigDecimal minimum_interest_rate_coefficient;
    private String minimum_interest_rate_coefficient_action;
    private BigDecimal maximal_interest_rate;
    private BigDecimal maximal_interest_rate_coefficient;
    private String maximal_interest_rate_coefficient_action;
}
