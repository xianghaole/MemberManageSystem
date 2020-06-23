package cn.lger.dao;

import cn.lger.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IntegralDao extends JpaRepository<Member,String> {
    @Transactional
    @Modifying
    @Query(value = "update member m set m.member_integral = 0 where m.id = ?1",nativeQuery = true)
    public void changeIntegral(String id);


    @Modifying
    @Transactional
    @Query(value = "update member m set m.member_integral = ?2 where m.id = ?1",nativeQuery = true)
    void updateMemberIntegral(String id, Integer memberIntegral);
}
