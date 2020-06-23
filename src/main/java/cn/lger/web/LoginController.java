package cn.lger.web;

import cn.lger.dao.MessageDao;
import cn.lger.domain.Member;
import cn.lger.domain.MemberGrade;
import cn.lger.service.MemberGradeService;
import cn.lger.service.MemberService;
import cn.lger.util.FileUploadUtil;
import cn.lger.util.Md5;
import cn.lger.util.MemberNumberRandomUtil;
import cn.lger.util.UUIDRandomUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Code that Changed the World
 * Pro said
 * Created by Pro on 2017-12-19.
 */
@Controller
public class LoginController {
    @Autowired
    private MemberService memberService;
    @Resource
    private BCryptPasswordEncoder encoder;
    @Resource
    private MemberGradeService memberGradeService;
    @Resource
    private MessageDao messageDao;
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginView(HttpServletRequest request) {
        if (request.getSession().getAttribute("member") != null)
            return "index";
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("error", "用户名或者密码错误");
        return "login";
    }
    @RequestMapping(value = "/login2", method = RequestMethod.POST)
    public String login2(String username, String password, Map<String,Object> map, HttpSession session) {
        System.out.println(encoder.encode(password));
        Member member = memberService.findByMemberNameAndPassword(username, Md5.MD5(password));
        if(member==null){
            map.put("error","用户名或者密码错误");
            return "login";
        }else if("停用".equals(member.getState())){
                map.put("error","对不起，您的账号被停用，请联系管理员");
                return "login";
        }else if("挂失".equals(member.getState())) {
            map.put("error", "对不起，您的账号不存在");
            return "login";
        }
        else{
            session.setAttribute("member",member);
            return "index_member";
        }
    }
    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    @PostMapping("/registerMember")
    public String addMember(Member member, String gradeName, MultipartFile icon, Map<String, Object> model) {
        System.out.println(member);
        //处理上传文件
        try {
            if (icon == null)
                return "error";
            if (icon.getOriginalFilename().equals(""))
                member.setIconPath("/assets/icon/common.jpg");
            else
                member.setIconPath(FileUploadUtil.upload(icon, "/assets/icon/", UUIDRandomUtil.get32UUID()));
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        member.setId(MemberNumberRandomUtil.randomMemberNumber());
        //通过会员等级名获取会员类型
        List<MemberGrade> list = memberGradeService.findMemberGradeByGradeName(gradeName);
        //保证输入的会员名是存在的
        if (list.get(0) == null)
            return "error";
        //设置会员类型
        member.setMemberGrade(list.get(0));
        member.setState("正常");
        member.setBalance((float) 10000);
        member.setMemberIntegral(0L);
        member.setPassword(Md5.MD5(member.getPassword()));
//        System.out.println(member);
        member = memberService.addMember(member);

        model.put("member", member);
        return "login";
    }

    @RequestMapping("/index_member")
    public String indexMember(){
        return "index_member";
    }

}
