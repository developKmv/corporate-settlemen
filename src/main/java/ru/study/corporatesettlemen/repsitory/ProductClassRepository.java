package ru.study.corporatesettlemen.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.study.corporatesettlemen.entity.ProductClass;
import ru.study.corporatesettlemen.entity.ProductRegisterType;

import java.util.List;

public interface ProductClassRepository extends JpaRepository<ProductClass, Long> {
}
