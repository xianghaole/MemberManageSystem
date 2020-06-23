package cn.lger.web;

import cn.lger.dao.BaseEntityDao;
import cn.lger.domain.BaseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class CountController {
    @Resource
    private BaseEntityDao baseEntityDao;
    @RequestMapping("/toCountEchart")
    public String toCountEchart(){
        return "memberGradeCount";
    }
//    加载系统动态数据
    @RequestMapping("/loadCountEchart")
    @ResponseBody
    public List<BaseEntity> loadCountEchart(){
        return baseEntityDao.findByname();
    }
}
