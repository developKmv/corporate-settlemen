package ru.study.corporatesettlemen.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.study.corporatesettlemen.entity.AccountPool;

import java.util.List;

public interface AccountPoolRepository extends JpaRepository<AccountPool,Long> {
 /*   @Query("select ap from AccountPool ap where ap.branch_code = ?1 and ap.currency_code = ?2 and ap.mdm_code=?3 and ap.priority_code = ?4 and ap.registry_type_code = ?5")
    List<AccountPool> findAccNumber(String branchCode,String currencyCode,String mdmCode,String priorityCode,String registryTypeCode);*/
 @Query("select ap from AccountPool ap where ap.registry_type_code = ?1")
    List<AccountPool> findAccNumber(String val);
}