package ru.study.corporatesettlemen.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseProduct {
    private Long instance;
    private List<Long> registerId;
    private List<Long> supplementaryAgreement;
}
