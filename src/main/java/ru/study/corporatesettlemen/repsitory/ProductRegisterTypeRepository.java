package ru.study.corporatesettlemen.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.study.corporatesettlemen.entity.ProductRegisterType;

import java.util.List;

public interface ProductRegisterTypeRepository extends JpaRepository<ProductRegisterType,Long> {
   /*@Query(value = "select rt.* from tpp_ref_product_register_type rt inner join tpp_ref_product_class pc on rt.product_class_code=pc.value where pc.value=?1 and rt.account_type='Клиентский'",nativeQuery = true)
    List<ProductRegisterType> findRelativeRegistryTypes(String value);*/
    List<ProductRegisterType> findByValue(String value);
}
