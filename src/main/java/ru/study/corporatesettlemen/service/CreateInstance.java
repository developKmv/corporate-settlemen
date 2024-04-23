package ru.study.corporatesettlemen.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.study.corporatesettlemen.api.RequestProduct;
import ru.study.corporatesettlemen.api.ResponseProduct;
import ru.study.corporatesettlemen.entity.Agreement;
import ru.study.corporatesettlemen.entity.Product;
import ru.study.corporatesettlemen.entity.ProductRegister;
import ru.study.corporatesettlemen.entity.ProductRegisterType;
import ru.study.corporatesettlemen.exception.ServiceException;
import ru.study.corporatesettlemen.inspects.CheckResult;
import ru.study.corporatesettlemen.inspects.Checking;
import ru.study.corporatesettlemen.inspects.CheckingDuplicatesInstanceCreate;
import ru.study.corporatesettlemen.repsitory.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
@NoArgsConstructor
@Slf4j
public class CreateInstance {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductClassRepository productClassRepository;
    @Autowired
    ProductRegisterTypeRepository productRegisterTypeRepository;
    @Autowired
    ServiceQueryRepository serviceQueryRepository;
    @Autowired
    ProductRegisterRepository productRegisterRepository;
    @Autowired
    AgreementRepository agreementRepository;

    public ResponseProduct create(List<Checking> checks, RequestProduct rq) throws ServiceException {
        CheckingDuplicatesInstanceCreate checkingDuplicates = (CheckingDuplicatesInstanceCreate) checks.get(0);
        CheckResult result = checkingDuplicates.check(rq);
        if (!result.getStatus()){
            ServiceException exp =new ServiceException(result.getMessage(), new Throwable(result.getMessage()));
            exp.setHttpMethodType(result.getOptions());
            throw exp;
        }

        List<ProductRegisterType> relativeProductType = serviceQueryRepository.pruductClassFindByValue(rq.getProductCode());
        if (relativeProductType.size() == 0){
            ServiceException exp = new ServiceException(String.format("КодПродукта %s не найдено в Каталоге продуктов tpp_ref_product_class", rq.getProductCode()), new Throwable(""));
            exp.setHttpMethodType(result.getOptions());
            throw exp;
        }

        Product product = productRepository.save(new Product(null, new BigInteger("1000"), new BigInteger(String.valueOf(rq.getContractid())),
                rq.getProductType().toString(), rq.getContractNumber(), new BigInteger(String.valueOf(rq.getPriority())),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), new BigInteger("300"),
                new BigDecimal("123"), new BigDecimal("888"), new BigDecimal("888"), "requisite", rq.getRateType().toString(), new BigDecimal(rq.getTaxPercentageRate()),
                "hhhh", "active"));
        List<Long> registerIds = new ArrayList<>();
        for (ProductRegisterType e : relativeProductType){
            ProductRegister productRegister = productRegisterRepository.
                    save(new ProductRegister(null,new BigInteger(String.valueOf(product.getId())),e,
                            new BigInteger("33"),"810", ProductRegister.State.Открыт,"fghfgh"));

            registerIds.add(productRegister.getId());
        }

        List<Agreement> agreements = agreementRepository.findByProductId(product);
        List<Long> agreementsIds = new ArrayList<>();
        agreements.stream().forEach(e->{
            agreementsIds.add(e.getId());
        });

        return new ResponseProduct(product.getId(),registerIds,agreementsIds);
    }

}
