package ru.study.corporatesettlemen.repsitory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.study.corporatesettlemen.entity.ProductClass;
import ru.study.corporatesettlemen.entity.ProductRegisterType;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceQueryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<ProductRegisterType> pruductClassFindByValue(String value){
        TypedQuery<ProductClass> queryProductClass = entityManager.createQuery(
                "select pc from ProductClass pc where pc.value =?1"
                ,ProductClass.class);
        queryProductClass.setParameter(1,value);
        List<ProductClass> productClass = queryProductClass.getResultList();

        List<String> listValues = new ArrayList<>();
        productClass.forEach(e->listValues.add(e.getValue()));

        TypedQuery<ProductRegisterType> queryProductRegisterType = entityManager.createQuery(
                "select rt from ProductRegisterType rt where rt.product_class.value in :listP and rt.account_type.value ='Клиентский'", ProductRegisterType.class);
        queryProductRegisterType.setParameter("listP",listValues);
        List<ProductRegisterType> productRegisterTypes = queryProductRegisterType.getResultList();

        return productRegisterTypes;
    }

}
