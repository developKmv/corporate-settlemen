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

import java.util.Optional;
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Order(2)
public class CheckingDuplicatesInstanceUpdate implements Checking{
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AgreementRepository agreementRepository;

    public CheckResult check(RequestProduct rq) {
        Agreement agreement = null;
        CheckResult result = new CheckResult();
        Optional<Product> product = productRepository.findById(Long.valueOf(rq.getInstanceid()));
        if(product.isEmpty()){
            result.setStatus(Boolean.parseBoolean("false"));
            result.setMessage(String.format("Экземпляр продукта с параметром instanceId %s не найден.",rq.getInstanceid()));
            result.setOptions("Not_Found");
            return result;
        }

        for (InstanceArrangementField e : rq.getInstanceArrangement()){
            agreement = agreementRepository.findByNumber(e.getNumber());
        }

        if(agreement != null){
            result.setStatus(Boolean.parseBoolean("false"));
            result.setMessage(String.format("Параметр № Дополнительного соглашения (сделки) Number %s уже существует для ЭП с ИД  %d",agreement.getNumber(),rq.getInstanceid()));
            result.setOptions("Bad_Request");
            return result;
        }

        return result;
    }
    @Override
    public boolean check() {
        return false;
    }
}
