package cn.lger.dao;

import cn.lger.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeDao extends JpaRepository<Notice,Integer> {
}
