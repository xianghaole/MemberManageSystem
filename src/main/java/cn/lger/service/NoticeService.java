package cn.lger.service;

import cn.lger.dao.NoticeDao;
import cn.lger.domain.Notice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NoticeService {
    @Resource
    private NoticeDao noticeDao;

    public void updateNotice(Notice notice) {
        if (noticeDao.findById(notice.getNid())!=null){
            noticeDao.save(notice);
            return;
        }
        throw new RuntimeException("Commodity中不存在当前的id:"+notice.getNid());
    }
}
