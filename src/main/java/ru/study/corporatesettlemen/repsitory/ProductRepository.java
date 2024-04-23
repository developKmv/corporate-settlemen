package ru.study.corporatesettlemen.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.study.corporatesettlemen.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{
    Product findByNumber(String number);
}
