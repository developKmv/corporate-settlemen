package ru.study.corporatesettlemen.inspects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.study.corporatesettlemen.api.InstanceArrangementField;
import ru.study.corporatesettlemen.api.RequestProduct;
import ru.study.corporatesettlemen.entity.Agreement;
import ru.study.corporatesettlemen.entity.Product;
import ru.study.corporatesettlemen.repsitory.AgreementRepository;
import ru.study.corporatesettlemen.repsitory.ProductRepository;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Order(1)
public class CheckingDuplicatesInstanceCreate implements Checking {
    @Autowired
    AgreementRepository agreementRepository;
    @Autowired
    ProductRepository productRepository;

    public CheckResult check(RequestProduct rq) {
        CheckResult result = new CheckResult();
        Product product = productRepository.findByNumber(rq.getContractNumber());
        log.info("product: " + product);
        if (product != null) {
            result.setStatus(Boolean.parseBoolean("false"));
            result.setMessage(String.format("Параметр contractNumber № договора %s уже существует для ЭП с ИД %s", product.getNumber(), product.getId()));
            result.setOptions("Bad_Request");
        }

        for (InstanceArrangementField e : rq.getInstanceArrangement()) {
            Agreement agreement = agreementRepository.findByNumber(e.getNumber());
            log.info("agreement: " + agreement);
            if (agreement != null) {
                result.setStatus(Boolean.parseBoolean("false"));
                result.setMessage(String.format("Параметр № Дополнительного соглашения (сделки) Number %s уже существует для ЭП с ИД  %s",
                        agreement.getNumber(), agreement.getId()));
                result.setOptions("Not_Found");
                return result;
            }
        }
        return result;
    }

    @Override
    public boolean check() {
        return false;
    }
}
