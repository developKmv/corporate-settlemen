package ru.study.corporatesettlemen.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.study.corporatesettlemen.api.InstanceArrangementField;
import ru.study.corporatesettlemen.api.RequestProduct;
import ru.study.corporatesettlemen.api.ResponseProduct;
import ru.study.corporatesettlemen.entity.Agreement;
import ru.study.corporatesettlemen.entity.Product;
import ru.study.corporatesettlemen.entity.ProductRegister;
import ru.study.corporatesettlemen.exception.ServiceException;
import ru.study.corporatesettlemen.inspects.CheckResult;
import ru.study.corporatesettlemen.inspects.Checking;
import ru.study.corporatesettlemen.inspects.CheckingDuplicatesInstanceUpdate;
import ru.study.corporatesettlemen.repsitory.AgreementRepository;
import ru.study.corporatesettlemen.repsitory.ProductRegisterRepository;
import ru.study.corporatesettlemen.repsitory.ProductRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@Data
public class UpdateInstance {
    @Autowired
    AgreementRepository agreementRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductRegisterRepository productRegisterRepository;

    public ResponseProduct update(List<Checking> checks,RequestProduct rq) throws ServiceException {
        CheckingDuplicatesInstanceUpdate checkingDuplicatesUpdate = (CheckingDuplicatesInstanceUpdate)checks.get(1);
        CheckResult result = checkingDuplicatesUpdate.check(rq);

        if (!result.getStatus()){
            ServiceException exp =new ServiceException(result.getMessage(), new Throwable(result.getMessage()));
            exp.setHttpMethodType(result.getOptions());
            throw exp;
        }
        Optional<Product> product = productRepository.findById(Long.valueOf(rq.getInstanceid()));

        for(InstanceArrangementField e :rq.getInstanceArrangement()){
            agreementRepository.save(new Agreement(null,product.get(),e.getGeneralAgreementId(),e.getSupplementaryAgreementId(),e.getArrangementType(),
                    new BigInteger(String.valueOf(e.getShedulerJobId())),e.getNumber(),new Timestamp(e.getOpeningDate().getTime()),new Timestamp(e.getClosingDate().getTime()),
                    new Timestamp(e.getCancelDate().getTime()),new BigInteger(String.valueOf(e.getValidityDuration())),e.getCancellationReason(),e.getStatus(),
                    new Timestamp(e.getInterestCalculationDate().getTime()),e.getInterestRate(),e.getCoefficient(),e.getCoefficientAction(), e.getMinimumInterestRate(), new BigDecimal(String.valueOf(e.getMinimumInterestRateCoefficient())),
                    e.getMinimumInterestRateCoefficientAction(),new BigDecimal(String.valueOf(e.getMaximalnterestRate())),new BigDecimal(String.valueOf(e.getMaximalnterestRateCoefficient())),
                    e.getMaximalnterestRateCoefficientAction()));
        }

        List<ProductRegister> productRegisters = productRegisterRepository.findAllById(List.of(product.get().getId()));
        List<Long> registerIds = new ArrayList<>();
        productRegisters.stream().forEach(e->{
            registerIds.add(e.getId());
        });

        List<Agreement> agreements = agreementRepository.findByProductId(product.get());
        List<Long> agreementsIds = new ArrayList<>();
        agreements.stream().forEach(e->{
            agreementsIds.add(e.getId());
        });

        return new ResponseProduct(product.get().getId(),registerIds,agreementsIds);
    }
}
