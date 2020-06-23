package cn.lger.web;

import cn.lger.dao.IntegralDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
public class IntegralController {
    @Resource
    private IntegralDao integralDao;
   @RequestMapping("/integral")
    public String integralList(){
       return "integral";
   }
   @RequestMapping("/changeIntegral")
   @ResponseBody
    public String changeIntegral(String id){
        integralDao.changeIntegral(id);
        return "success";
   }
    @RequestMapping("/updateMemberIntegral")
    @ResponseBody
    public String updateMemberIntegral(String id,Integer memberIntegral){
        integralDao.updateMemberIntegral(id,memberIntegral);
        return "success";
    }
}
