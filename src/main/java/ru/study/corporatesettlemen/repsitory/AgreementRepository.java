package ru.study.corporatesettlemen.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.study.corporatesettlemen.entity.Agreement;
import ru.study.corporatesettlemen.entity.Product;

import java.util.List;

public interface AgreementRepository extends JpaRepository<Agreement,Long> {
    Agreement findByNumber(String number);
    @Query("select ag from Agreement ag where ag.product_id = ?1")
    List<Agreement> findByProductId(Product productId);

}
