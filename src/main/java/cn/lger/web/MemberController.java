package cn.lger.web;

import cn.lger.dao.MemberDao;
import cn.lger.domain.Member;
import cn.lger.domain.MemberGrade;
import cn.lger.exception.IdNotFoundException;
import cn.lger.exception.IntegralNotEnoughException;
import cn.lger.service.GiftService;
import cn.lger.service.MemberGradeService;
import cn.lger.service.MemberService;
import cn.lger.util.FileUploadUtil;
import cn.lger.util.Md5;
import cn.lger.util.MemberNumberRandomUtil;
import cn.lger.util.UUIDRandomUtil;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Code that Changed the World
 * Pro said
 * Created by Pro on 2017-12-06.
 */

@Controller
public class MemberController {
    @Resource
    private MemberDao memberDao;
    @Resource
    private MemberService memberService;
    @Resource
    private MemberGradeService memberGradeService;
    @Resource
    private BCryptPasswordEncoder encoder;


    @GetMapping("/addMember")
    public String getAddMemberView() {
        return "addMember";
    }

    @PostMapping("/addMember")
    public String addMember(Member member, String gradeName, MultipartFile icon, Map<String, Object> model) {
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
        return "addMemberSuccess";
    }

    @GetMapping("/getGrade")
    @ResponseBody
    public List<MemberGrade> getGrade() {
        return memberGradeService.findAll();
    }

    @GetMapping("/queryMember")
    public String getQueryMemberView() {
        return "queryMember";
    }
    //修改密码
    @GetMapping("/updatePassword")
    public String updatePassword() {
        return "updatePassword";
    }
    //修改密码执行
    @PostMapping("/updateNewPassword")
    public String updateNewPassword(String id,String newPassword,Map map) {

        if(StringUtils.isEmpty(newPassword)){
            System.out.println(1);
            map.put("success","请输入新密码");
            return "updatePassword";
        }else{
                memberDao.updateNewPassword(id,Md5.MD5(newPassword));
                map.put("success","更新成功");
                return "updatePassword";
        }
    }


    @PostMapping("/queryMember")
    @ResponseBody
    public Page<Member> queryMember(Integer currentPage, String memberName) {
        if (memberName == null || memberName.trim().equals(""))

            return memberService.findMembers(currentPage);
        return memberService.findMembersByMemberName(currentPage, memberName);
    }

    @GetMapping("/modifyMemberState")
    public String getModifyMemberStateView() {
        return "modifyMemberState";
    }

    @PostMapping("/modifyMemberState")
    @ResponseBody
    public String modifyMemberStateView(String id, String state) {
        memberService.modifyMemberState(id, state);
        return "修改成功";
    }

    @GetMapping("/deleteMember")
    public String getDeleteMemberView() {
        return "deleteMember";
    }

    @PostMapping("/deleteMember")
    @ResponseBody
    public String deleteMember(String id) {
        try {
            memberService.deleteMember(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @GetMapping("/balanceRecharge")
    public String getBalanceRechargeView() {
        return "/balanceRecharge";
    }

    @PostMapping("/balanceRecharge")
    @ResponseBody
    public String balanceRecharge(String id, String balance) {
        try {
            memberService.balanceRecharge(id, balance);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @GetMapping("/integralLottery")
    public String getIntegralLotteryView() {
        return "integralLottery";
    }

    @PostMapping("/integralLottery")
    @ResponseBody
    public Member integralLottery(Integer allIntegral) {
        if (allIntegral == null)
            return null;
        try {
            return memberService.integralLottery(allIntegral);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("/me")
    public String me(){
        return "me";
    }
}
