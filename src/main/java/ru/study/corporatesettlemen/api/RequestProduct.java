package ru.study.corporatesettlemen.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestProduct {
    private Integer instanceid;
    @NotNull(message = "productType is null")
    private ProductType productType;
    @NotBlank(message = "productCode is blanc")
    private String productCode;
    @NotBlank(message = "registerType is blanc")
    private String registerType;
    @NotBlank(message = "mdmCode is blanc")
    private String mdmCode;
    @NotBlank(message = "contractNumber is blanc")
    private String contractNumber;
    @NotNull(message = "contractDate is blanc")
    private Date contractDate;
    @NotNull(message = "priority is blanc")
    private Integer priority;
    private Float minimalBalance;
    private Float thresholdAmount;
    private String accountingDetails;
    private RateType rateType;
    private Float taxPercentageRate;
    private Float technicalOverdraftLimitAmount;
    @NotNull(message = "contractid is blanc")
    private Integer contractid;
    @NotBlank(message = "branchCode is blanc")
    private String branchCode;
    @NotBlank(message = "isoCurrencyCode is blanc")
    private String isoCurrencyCode;
    @NotBlank(message = "urgencyCode is blanc")
    private String urgencyCode = "00";
    @NotNull(message = "referenceCode is blanc")
    private Integer referenceCode;
    private Map<String,AdditionalProperty> additionalPropertiesVip;
    private InstanceArrangementField[] instanceArrangement;


    public enum ProductType{
        НСО,СМО,ЕЖО,ДБДС,Договор;
    }

    public enum RateType{
        дифференцированная,прогрессивная;
    }

}

