package ru.study.corporatesettlemen.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.study.corporatesettlemen.api.RequestAccount;
import ru.study.corporatesettlemen.api.RequestProduct;
import ru.study.corporatesettlemen.api.ResponseAccount;
import ru.study.corporatesettlemen.entity.AccountPool;
import ru.study.corporatesettlemen.entity.ProductRegister;
import ru.study.corporatesettlemen.entity.ProductRegisterType;
import ru.study.corporatesettlemen.exception.ServiceException;
import ru.study.corporatesettlemen.inspects.CheckResult;
import ru.study.corporatesettlemen.inspects.Checking;
import ru.study.corporatesettlemen.inspects.CheckingDuplicatesAccountCreate;
import ru.study.corporatesettlemen.repsitory.AccountPoolRepository;
import ru.study.corporatesettlemen.repsitory.ProductRegisterRepository;
import ru.study.corporatesettlemen.repsitory.ProductRegisterTypeRepository;

import java.math.BigInteger;
import java.util.List;

@Service
@NoArgsConstructor
public class CreateAccount {
    @Autowired
    ProductRegisterTypeRepository productRegisterTypeRepository;
    @Autowired
    AccountPoolRepository accountPoolRepository;
    @Autowired
    ProductRegisterRepository productRegisterRepository;
    public ResponseAccount create(List<Checking> checks, RequestAccount rq) throws ServiceException {
        ResponseAccount responseAccount = new ResponseAccount();
        CheckingDuplicatesAccountCreate checkingDuplicatesAccountCreate = (CheckingDuplicatesAccountCreate) checks.get(2);
        CheckResult result = checkingDuplicatesAccountCreate.check(rq);

        if (!result.getStatus()) {
            ServiceException exp = new ServiceException(result.getMessage(), new Throwable(result.getMessage()));
            exp.setHttpMethodType(result.getOptions());
            throw exp;
        }
        List<ProductRegisterType> productRegisterTypes = productRegisterTypeRepository.findByValue(rq.getRegistryTypeCode());

        if (productRegisterTypes.size() == 0) {
            ServiceException exp = new ServiceException(String.format(" Код Продукта %s не найдено в Каталоге продуктов tpp_ref_product_register_type для данного типа Регистра", rq.getRegistryTypeCode()), new Throwable(""));
            exp.setHttpMethodType("Not_found");
            throw exp;
        }

        List<AccountPool> accountPools = accountPoolRepository.findAccNumber(rq.getRegistryTypeCode());
        ProductRegister productRegisterType = productRegisterRepository.save(new ProductRegister(null,new BigInteger(String.valueOf(rq.getInstanceId())),
                productRegisterTypes.get(0),new BigInteger(String.valueOf(accountPools.get(0).getId())), rq.getCurrencyCode(), ProductRegister.State.Открыт,"sdfsdf"));

        responseAccount.setAccountId(String.valueOf(productRegisterType.getId()));
        return responseAccount;
    }
}
