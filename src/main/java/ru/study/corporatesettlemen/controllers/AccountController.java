package ru.study.corporatesettlemen.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.study.corporatesettlemen.api.RequestAccount;
import ru.study.corporatesettlemen.api.ResponseAccount;
import ru.study.corporatesettlemen.inspects.Checking;
import ru.study.corporatesettlemen.inspects.CheckingDuplicatesAccountCreate;
import ru.study.corporatesettlemen.service.CreateAccount;

import java.util.List;

@RestController
@RequestMapping(path = "/corporate-settlement-account", produces = "application/json")
public class AccountController {
    @Autowired
    List<Checking> checks;
    @Autowired
    CreateAccount createAccount;
    @PostMapping(path = "create", consumes = "application/json")
    public ResponseEntity<ResponseAccount> create(@Valid @RequestBody RequestAccount rq) {
        ResponseAccount responseAccount = new ResponseAccount();
        responseAccount = createAccount.create(checks,rq);

        return new ResponseEntity<ResponseAccount>(responseAccount,HttpStatus.OK);
    }
}
