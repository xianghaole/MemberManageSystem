package cn.lger.web;

import cn.lger.dao.GiftDao;
import cn.lger.domain.Commodity;
import cn.lger.domain.Gift;
import cn.lger.exception.GiftNumberNotEnoughException;
import cn.lger.exception.IdNotFoundException;
import cn.lger.exception.IntegralNotEnoughException;
import cn.lger.service.GiftService;
import cn.lger.util.FileUploadUtil;
import cn.lger.util.UUIDRandomUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Code that Changed the World
 * Pro said
 * Created by Pro on 2017-12-18.
 */
@Controller
public class GiftController {
    @Resource
    private GiftDao giftDao;
    @Resource
    private GiftService giftService;

    @GetMapping("/setGift")
    public String getSetGiftView(){
        return "setGift";
    }

    @PostMapping("/setGift")
    @ResponseBody
    public String setGift(Gift gift){
        try{
            giftService.add(gift);
        } catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
    @PostMapping("/addGift")

    public String addGift(Gift gift, @RequestParam(value = "filePath") MultipartFile filePath){
        System.out.println(gift);
        //处理上传文件
        //处理上传文件
        try {
            if (filePath == null)
                return "error";
            if (filePath.getOriginalFilename().equals(""))
                gift.setGiftImg("/assets/icon/common.jpg");
            else
                gift.setGiftImg(FileUploadUtil.upload(filePath, "/assets/icon/", UUIDRandomUtil.get32UUID()));
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        gift.setGiftTime(format);
        System.out.println(gift);
        try{
            giftService.add(gift);
            return "redirect:modifyGiftNumber";
        } catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/queryGift")
    @ResponseBody
    public Page<Gift> queryGift(Integer currentPage){
        return giftService.findGifts(currentPage);
    }


    @GetMapping("/modifyGiftNumber")
    public String getModifyGiftNumberView(){
        return "modifyGiftNumber";
    }


    @PostMapping("/modifyGiftNumber")
    @ResponseBody
    public String modifyGiftNumber(Integer giftNumber, Integer giftId){
        try{
            if (giftNumber == null || giftNumber <= 0 || giftId == null)
                return "error";
            giftService.modifyGiftNumber(giftNumber, giftId);
        } catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @PostMapping("/deleteGift")
    @ResponseBody
    public String deleteGift(Integer giftId){
        try{
            if (giftId == null)
                return "error";
            giftService.deleteGift(giftId);
        } catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @GetMapping("/integralExchange")
    public String getIntegralExchangeView(Map<String, Object> model){
        model.put("gifts", giftService.findAll());
        return "/integralExchange";
    }

    @PostMapping("/integralExchange")
    @ResponseBody
    public String integralExchange(String memberId, Integer giftId) {
        try {
            if (giftId == null || memberId == null || "".equals(memberId)) {
                return "输入不正确！";
            }
            giftService.integralExchange(memberId, giftId);
        } catch (IntegralNotEnoughException e) {
            e.printStackTrace();
            return "会员积分不足";
        } catch (IdNotFoundException e) {
            e.printStackTrace();
            return "会员账号不存在";
        } catch (GiftNumberNotEnoughException e) {
            e.printStackTrace();
            return "礼品已经下架";
        }
        return "礼品兑换成功";
    }
//    前台礼品功能开始
    @RequestMapping("/giftList")
    public String giftList(Map<String,Object> map){
        List<Gift> giftList = giftDao.findAll();
        map.put("giftList",giftList);
        return "giftList";
    }
}
