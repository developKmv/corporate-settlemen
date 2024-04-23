package ru.study.corporatesettlemen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name ="account_pool_id",referencedColumnName = "id")
    private AccountPool account_pool_id;
    private String account_number;
    private Boolean bussy;
}
