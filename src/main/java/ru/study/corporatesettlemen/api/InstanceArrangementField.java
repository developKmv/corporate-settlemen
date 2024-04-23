package ru.study.corporatesettlemen.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstanceArrangementField{
    private String GeneralAgreementId;
    private String SupplementaryAgreementId;
    private String arrangementType;
    private Integer shedulerJobId;
    @NotBlank(message = "Field Number is Null")
    private String Number;
    @NotNull(message = "Field openingDate is Null")
    private Date openingDate;
    private Date closingDate;
    private Date cancelDate;
    private Integer validityDuration;
    private String cancellationReason;
    private String status;
    private Date interestCalculationDate;
    private Float interestRate;
    private Float coefficient;
    private String coefficientAction;
    private Float minimumInterestRate;
    private String minimumInterestRateCoefficient;
    private String minimumInterestRateCoefficientAction;
    private BigDecimal maximalnterestRate;
    private BigDecimal maximalnterestRateCoefficient;
    private String maximalnterestRateCoefficientAction;
}