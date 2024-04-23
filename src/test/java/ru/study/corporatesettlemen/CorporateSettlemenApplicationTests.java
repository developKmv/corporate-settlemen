package ru.study.corporatesettlemen;

import jakarta.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.study.corporatesettlemen.api.AdditionalProperty;
import ru.study.corporatesettlemen.api.InstanceArrangementField;
import ru.study.corporatesettlemen.api.RequestAccount;
import ru.study.corporatesettlemen.api.RequestProduct;
import ru.study.corporatesettlemen.entity.Agreement;
import ru.study.corporatesettlemen.entity.Product;
import ru.study.corporatesettlemen.entity.ProductRegister;
import ru.study.corporatesettlemen.inspects.*;
import ru.study.corporatesettlemen.repsitory.AgreementRepository;
import ru.study.corporatesettlemen.repsitory.ProductRegisterRepository;
import ru.study.corporatesettlemen.repsitory.ProductRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;
@Slf4j
@SpringBootTest
class CorporateSettlemenApplicationTests {
    @Autowired
    List<Checking> checks;
    @Autowired
    AgreementRepository agreementRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductRegisterRepository productRegisterRepository;
    private static Validator validator;
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine");
    static RequestProduct rq;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        postgres.start();

        rq = new RequestProduct(1, RequestProduct.ProductType.НСО, "prodCode", "regType", "mdmC", "10", new Date(), 1, 4f, 5f, "ss",
                RequestProduct.RateType.дифференцированная, 1f, 4f, 34, "code", "iso", "fff", 5,
                Map.of("first", new AdditionalProperty("key", "val", "n"))
                , new InstanceArrangementField[]{new InstanceArrangementField("h", "11", "22", 1, "rr", new Date(), new Date(), new Date(), 1, "dd", "dd",
                new Date(), 1f, 4f, "33", 5f, "33", "44", new BigDecimal(123), new BigDecimal(33), "dd")});
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        log.info("url: " + postgres.getJdbcUrl());
        registry.add("spring.datasource.username", postgres::getUsername);
        log.info("userName: " + postgres.getUsername());
        registry.add("spring.datasource.password", postgres::getPassword);
        log.info("password: " + postgres.getPassword());
    }

    @Test
    public void testRequestProductValid() {
        RequestProduct product = new RequestProduct(null, null, "", "", "", "", new Date(), 1, 4f, 5f, "ss",
                RequestProduct.RateType.дифференцированная, 1f, 4f, 34, "", "", "", 5,
                Map.of("first", new AdditionalProperty("key", "val", "n"))
                , new InstanceArrangementField[]{new InstanceArrangementField("h", "f", "f", 1, "rr", new Date(), new Date(), new Date(), 1, "dd", "dd",
                new Date(), 1f, 4f, "fff", 5f, "fff", "fff", new BigDecimal(123), new BigDecimal(33), "dd")});


        Set<ConstraintViolation<RequestProduct>> violations = validator.validate(product);
        //Вывод ошибок валидации
        System.out.println(violations.toString());
        //Кол-во ошибок валидации
        Assertions.assertEquals(8, violations.size());
    }
    @Test
    public void testCheckingDuplicatesInstanceCreate(){
        CheckingDuplicatesInstanceCreate duplicatesCreate = (CheckingDuplicatesInstanceCreate)checks.get(0);

        Product product = productRepository.save(new Product(null, new BigInteger("1000"), new BigInteger(String.valueOf(rq.getContractid())),
                rq.getProductType().toString(), rq.getContractNumber(), new BigInteger(String.valueOf(rq.getPriority())),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), new BigInteger("300"),
                new BigDecimal("123"), new BigDecimal("888"), new BigDecimal("888"), "requisite", rq.getRateType().toString(), new BigDecimal(rq.getTaxPercentageRate()),
                "hhhh", "active"));

        productRepository.save(product);
        for(InstanceArrangementField e :rq.getInstanceArrangement()){
            agreementRepository.save(new Agreement(null,product,e.getGeneralAgreementId(),e.getSupplementaryAgreementId(),e.getArrangementType(),
                    new BigInteger(String.valueOf(e.getShedulerJobId())),e.getNumber(),new Timestamp(e.getOpeningDate().getTime()),new Timestamp(e.getClosingDate().getTime()),
                    new Timestamp(e.getCancelDate().getTime()),new BigInteger(String.valueOf(e.getValidityDuration())),e.getCancellationReason(),e.getStatus(),
                    new Timestamp(e.getInterestCalculationDate().getTime()),e.getInterestRate(),e.getCoefficient(),e.getCoefficientAction(), e.getMinimumInterestRate(), new BigDecimal(String.valueOf(e.getMinimumInterestRateCoefficient())),
                    e.getMinimumInterestRateCoefficientAction(),new BigDecimal(String.valueOf(e.getMaximalnterestRate())),new BigDecimal(String.valueOf(e.getMaximalnterestRateCoefficient())),
                    e.getMaximalnterestRateCoefficientAction()));
        }

        CheckResult result= duplicatesCreate.check(rq);
        //System.out.println(result);
        Assertions.assertEquals("Параметр № Дополнительного соглашения (сделки) Number rr уже существует для ЭП с ИД  1",result.getMessage());
    }

    @Test
    public void testCheckingDuplicatesInstanceUpdate(){
        CheckingDuplicatesInstanceUpdate checkingDuplicatesUpdate = (CheckingDuplicatesInstanceUpdate)checks.get(1);
        rq.setInstanceid(1233);
        CheckResult result = checkingDuplicatesUpdate.check(rq);
        System.out.println(result.getMessage());
        Assertions.assertEquals("Экземпляр продукта с параметром instanceId 1233 не найден.",result.getMessage());
    }

    @Test
    public void testRequestAccountValid() {

        RequestAccount requestAccount = new RequestAccount(null,"03.012.002_47533_ComSoLd","Клиентский","800","022","00","15","001","00","00","001");

        Set<ConstraintViolation<RequestAccount>> violations = validator.validate(requestAccount);
        //Вывод ошибок валидации
        System.out.println(violations.toString());
        //Кол-во ошибок валидации
        Assertions.assertEquals(1, violations.size());
    }
    @Disabled
    @Test
    public void testCheckingDuplicatesAccountCreate(){
        RequestAccount requestAccount = new RequestAccount(null,"03.012.002_47533_ComSoLd","Клиентский","800","022","00","15","001","00","00","001");
        CheckingDuplicatesAccountCreate checkingDuplicates = (CheckingDuplicatesAccountCreate)checks.get(2);
        rq.setInstanceid(123);

        productRegisterRepository.save(new ProductRegister(123L,null,null,null,null,null,null));
        CheckResult result = checkingDuplicates.check(requestAccount);
        System.out.println(result.getMessage());
        Assertions.assertEquals("Экземпляр продукта с параметром instanceId 1233 не найден.",result.getMessage());
    }

    @Test
    void contextLoads() {
    }

}
