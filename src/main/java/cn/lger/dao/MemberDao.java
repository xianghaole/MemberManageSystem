package cn.lger.dao;

import cn.lger.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Code that Changed the World
 * Pro said
 * Created by Pro on 2017-12-06.
 */
public interface MemberDao extends JpaRepository<Member, String> {

    Member findMemberById(String id);



    @Query("select m from Member m where m.memberName = ?1")
    Page<Member> findAllByMemberName(String memberName, Pageable pageable);

    @Query("select count(m.id) from  Member  m")
    int queryAllCount();

    List<Member> findByBirthday(LocalDate birthday);
    @Query(value = "select * from member where member_name = ?1 and password = ?2" ,nativeQuery = true)
    Member findByMemberNameAndPassword(String memberName,String password);
    @Transactional
    @Modifying
    @Query(value = "update member m set m.password =?2 where m.id = ?1",nativeQuery = true)
    public void updateNewPassword(String id,String newPassword);
}
