package ru.study.corporatesettlemen.inspects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckResult {
    private Boolean status = true;
    private String message;
    private String options;
}
