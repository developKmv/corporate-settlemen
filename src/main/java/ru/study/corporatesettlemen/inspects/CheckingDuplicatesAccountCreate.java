package ru.study.corporatesettlemen.inspects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.study.corporatesettlemen.api.RequestAccount;
import ru.study.corporatesettlemen.api.RequestProduct;
import ru.study.corporatesettlemen.entity.ProductRegister;
import ru.study.corporatesettlemen.repsitory.ProductRegisterRepository;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Order(3)
public class CheckingDuplicatesAccountCreate implements Checking{
    @Autowired
    ProductRegisterRepository productRegisterRepository;
    public CheckResult check(RequestAccount rq) {
        CheckResult result = new CheckResult();
        ProductRegister productRegister = productRegisterRepository.findByTypeAndProductId(Long.valueOf(rq.getInstanceId()),rq.getRegistryTypeCode());
        if (productRegister != null){
            result.setStatus(Boolean.parseBoolean("false"));
            result.setMessage(String.format("Параметр registryTypeCode тип регистра %s уже существует для ЭП с ИД %d.",productRegister.getType().getValue(),productRegister.getProduct_id()));
            result.setOptions("Bad_Request");
        }


        return result;
    }
    @Override
    public boolean check() {
        return false;
    }
}
