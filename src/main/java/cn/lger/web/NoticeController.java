package cn.lger.web;

import cn.lger.dao.AdminDao;
import cn.lger.dao.NoticeDao;
import cn.lger.domain.Admin;
import cn.lger.domain.Commodity;
import cn.lger.domain.Notice;
import cn.lger.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
public class NoticeController {
    @Resource
    private NoticeDao noticeDao;
    @Resource
    private AdminDao adminDao;
    @Resource
    private NoticeService noticeService;
//    去发布公告页面
    @RequestMapping("/toAddNotice")
    public String toAddNotice(){
        return "notice";
    }
//    发表公告
    @RequestMapping("/addNotice")
    @ResponseBody
    public String addNotice(String nTitle,Integer hId,String content){
        log.info("hid:"+hId);
        Admin admin = adminDao.findById(hId).get();
//        log.info(nTitle);
//        log.info(content);
        Notice notice = new Notice();
        notice.setAdmin(admin);
        notice.setNTitle(nTitle);
        notice.setNContent(content);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        notice.setNTime(format);
        noticeDao.save(notice);
        return "success";
    }

//    前台查看公告列表
    @RequestMapping("/queryNotice")
    public String queryNotice(Map<String,Object> map){
        List<Notice> all = noticeDao.findAll();
        map.put("list",all);
        return "notice_query";
    }
//    查看公告详情
    @RequestMapping("/noticeDetail")
    public String noticeDetail(Integer nid,Map<String,Object> map){
        Notice notice = noticeDao.findById(nid).get();
        map.put("notice",notice);
        return "noticeDetail";
    }
//查询所有公告信息
@PostMapping("/queryAllNotice")
@ResponseBody
public Page<Notice> queryAllNotice(Integer currentPage){
    if (currentPage == null || currentPage < 0){
        currentPage = 0;
    }
    Pageable pageable = new PageRequest(currentPage, 5, Sort.Direction.DESC,"nid");
    return noticeDao.findAll(pageable);
}
//去公告列表页面
    @GetMapping("/queryAllNotice")
    public String getModifyCommodityView(){
        return "noticeList";
    }
//    更新公告
    @PostMapping("/updateNotice")
    @ResponseBody
    public String updateNotice(Notice notice){
        try{
            noticeService.updateNotice(notice);
        } catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
//删除公告
    @GetMapping("/delNotice")
    @ResponseBody
    public String delNotice(Integer nid){
        System.out.println(nid);
        try{
            noticeDao.deleteById(nid);
        } catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
}
