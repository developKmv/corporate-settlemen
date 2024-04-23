package ru.study.corporatesettlemen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_pool")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String branch_code;
    private String currency_code;
    private String mdm_code;
    private String priority_code;
    private String registry_type_code;
}
