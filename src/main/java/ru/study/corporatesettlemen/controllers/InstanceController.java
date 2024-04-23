package ru.study.corporatesettlemen.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.study.corporatesettlemen.api.AdditionalProperty;
import ru.study.corporatesettlemen.api.InstanceArrangementField;
import ru.study.corporatesettlemen.api.RequestProduct;
import ru.study.corporatesettlemen.api.ResponseProduct;
import ru.study.corporatesettlemen.inspects.Checking;
import ru.study.corporatesettlemen.service.CreateInstance;
import ru.study.corporatesettlemen.service.UpdateInstance;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping(path = "/corporate-settlement-instance", produces = "application/json")
public class InstanceController {
    @Autowired
    CreateInstance createInstance;
    @Autowired
    UpdateInstance updateInstance;
    @Autowired
    List<Checking> checks;

    @PostMapping(path = "create", consumes = "application/json")
    public ResponseEntity<ResponseProduct> create(@Valid @RequestBody RequestProduct rq) {
        ResponseProduct responseProduct = new ResponseProduct();

        if(rq.getInstanceid() == null){
            responseProduct = createInstance.create(checks,rq);
        }else {
            responseProduct = updateInstance.update(checks,rq);
        }

        return new ResponseEntity<>(responseProduct, HttpStatus.OK);
    }

    @GetMapping(path = "getProduct", consumes = "application/json")
    public RequestProduct getProduct() {

        return new RequestProduct(1, RequestProduct.ProductType.НСО, "prodCode", "regType", "mdmC", "num", new Date(), 1, 4f, 5f, "ss",
                RequestProduct.RateType.дифференцированная, 1f, 4f, 34, "code", "iso", "fff", 5,
                Map.of("first", new AdditionalProperty("key", "val", "n"))
                , new InstanceArrangementField[]{new InstanceArrangementField("h", "f", "f", 1, "rr", new Date(), new Date(), new Date(), 1, "dd", "dd",
                new Date(), 1f, 4f, "fff", 5f, "fff", "fff", new BigDecimal(123), new BigDecimal(33), "dd")});
    }
}
