package ru.study.corporatesettlemen.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity
@Table(name = "tpp_product_register")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigInteger product_id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "type",referencedColumnName = "value")
    private ProductRegisterType type;
    private BigInteger account;
    private String currency_code;
    private State state;
    private String account_number;


    public enum State{
        Удалён,Закрыт,Открыт,Зарезервирован;
    }
}
