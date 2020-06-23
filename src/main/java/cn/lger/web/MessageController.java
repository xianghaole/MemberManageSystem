package cn.lger.web;

import cn.lger.dao.MemberDao;
import cn.lger.dao.MessageDao;
import cn.lger.domain.Member;
import cn.lger.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MessageController {
    @Resource
    private MessageDao messageDao;
    @Resource
    private MemberDao memberDao;
    @RequestMapping("/message")
    public String toMessage(){
        return "message";
    }

    @PostMapping("/addMessage")
    @ResponseBody
    public String addMessage(String id,Message message){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        message.setMessageTime(format);
        Member member = memberDao.findMemberById(id);
        message.setMember(member);
        messageDao.save(message);
        return "addMessage success";
    }
    @RequestMapping("/queryMessageWithPage")
    public String queryMessageWithPage(@RequestParam(value="currentPage",defaultValue = "1")Integer currentPage, @RequestParam(value="pageSize",defaultValue = "5")Integer pageSize, Map<String,Object> map){
        Pageable pageRequest = PageRequest.of(currentPage-1,pageSize, Sort.Direction.DESC, "messageTime");
        Page<Message> page = messageDao.findAll(pageRequest);
        map.put("messageList",page);
        return "queryMessage";
    }
    @RequestMapping("/queryMessage")
    public String queryMessage(Map<String,Object> map){
        List<Message> messageList = messageDao.findAll();
        map.put("messageList",messageList);
        return "queryMessage";
    }
}
