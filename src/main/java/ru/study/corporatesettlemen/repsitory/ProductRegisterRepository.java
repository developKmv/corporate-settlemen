package ru.study.corporatesettlemen.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.study.corporatesettlemen.entity.ProductRegister;

import java.util.List;

public interface ProductRegisterRepository extends JpaRepository<ProductRegister,Long> {
    @Query("select pr from ProductRegister pr where pr.product_id = ?1 and pr.type.value = ?2")
    ProductRegister findByTypeAndProductId(Long productId,String value);
}
