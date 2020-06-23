package cn.lger.dao;

import cn.lger.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BaseEntityDao extends JpaRepository<BaseEntity,Integer> {
    @Query(value = "select mg.grade_name as name , count(mg.id) as value from member m LEFT JOIN member_grade mg on m.member_grade_id = mg.id GROUP BY mg.grade_name" ,nativeQuery = true)
    List<BaseEntity> findByname();
}
