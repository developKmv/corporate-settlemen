package ru.study.corporatesettlemen.config;

import org.springframework.context.annotation.Configuration;
import ru.study.corporatesettlemen.inspects.Checking;
import ru.study.corporatesettlemen.inspects.CheckingDuplicatesAccountCreate;
import ru.study.corporatesettlemen.inspects.CheckingDuplicatesInstanceCreate;
import ru.study.corporatesettlemen.inspects.CheckingDuplicatesInstanceUpdate;

import java.util.List;

@Configuration
public class Config {
    public List<Checking> checks(){
        return List.of(new CheckingDuplicatesInstanceCreate(),
                new CheckingDuplicatesInstanceUpdate(),
                new CheckingDuplicatesAccountCreate());
    }
}
