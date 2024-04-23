package ru.study.corporatesettlemen.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalProperty{
    private String key;
    private String value;
    private String name;
}
