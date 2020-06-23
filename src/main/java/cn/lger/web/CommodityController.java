package cn.lger.web;

import cn.lger.dao.CommodityDao;
import cn.lger.domain.Commodity;
import cn.lger.exception.BalanceNotEnoughException;
import cn.lger.exception.CommodityNumberNotEnoughException;
import cn.lger.exception.IdNotFoundException;
import cn.lger.service.CommodityService;
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
 * Created by Pro on 2017-12-17.
 */
@Controller
public class CommodityController {
    @Resource
    private CommodityDao commodityDao;

    @Resource
    private CommodityService commodityService;

    @GetMapping("/addCommodity")
    public String getAddCommodityView(){
        return "addCommodity";
    }

    @PostMapping("/addCommodity")

    public String addCommodity(Commodity commodity,@RequestParam(value = "filePath") MultipartFile filePath){
        System.out.println(commodity);
        //处理上传文件
        //处理上传文件
        try {
            if (filePath == null)
                return "error";
            if (filePath.getOriginalFilename().equals(""))
                commodity.setCommodityImg("/assets/icon/common.jpg");
            else
                commodity.setCommodityImg(FileUploadUtil.upload(filePath, "/assets/icon/", UUIDRandomUtil.get32UUID()));
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        commodity.setCommodityTime(format);
        System.out.println(commodity);
        try{
            commodityService.add(commodity);
            return "redirect:modifyCommodity";
        } catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/purchaseCommodity")
    public String getPurchaseCommodityView(){
        return "purchaseCommodity";
    }

    @PostMapping("/purchaseCommodity")
    @ResponseBody
    public String purchaseCommodity(String memberId, String commodityId, boolean balance){
        try{
            commodityService.purchaseCommodity(memberId, commodityId, balance);
        } catch (CommodityNumberNotEnoughException e){
            e.printStackTrace();
            return "商品数量不足";
        } catch (BalanceNotEnoughException e){
            e.printStackTrace();
            return "会员余额不足";
        } catch (IdNotFoundException e){
            e.printStackTrace();
            return "会员账号或商品账号不存在";
        }
        return "success";
    }
//    前台商品购买开始
    @RequestMapping("purchaseCommodityWithFrontEnd")
    @ResponseBody
    public String purchaseCommodityWithFrontEnd(String memberId, String commodityId){
        try{
            commodityService.purchaseCommodityWithFrontEnd(memberId, commodityId);
        } catch (CommodityNumberNotEnoughException e){
            e.printStackTrace();
            return "商品数量不足";
        } catch (BalanceNotEnoughException e){
            e.printStackTrace();
            return "会员余额不足";
        } catch (IdNotFoundException e){
            e.printStackTrace();
            return "会员账号或商品账号不存在";
        }
        return "恭喜您,购买成功";
    }

//    前后商品购买结束


    @GetMapping("/modifyCommodity")
    public String getModifyCommodityView(){
        return "modifyCommodity";
    }

    @PostMapping("/updateCommodity")
    @ResponseBody
    public String updateCommodity(Commodity commodity){
        try{
            commodityService.updateMemberGrade(commodity);
        } catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
    @GetMapping("/delCommodity")
    @ResponseBody
    public String delCommodity(String id){
        System.out.println(id);
        try{
            commodityDao.deleteById(id);
        } catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
    @GetMapping("/commodityList")
    public String commodityList(Map<String,Object> map){
        List<Commodity> list = commodityDao.findAll();
        map.put("list",list);
        return "commodityList";
    }
    @GetMapping("/commodityListWithPage")
    public String commodityListWithPage(Map<String,Object> map,@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,@RequestParam(value = "pageSize",defaultValue = "3")Integer pageSize){
        Pageable pageable = PageRequest.of(currentPage,pageSize);
        Page<Commodity> list = commodityDao.findAll(pageable);
        map.put("list",list);
        return "commodityList";
    }
    @PostMapping("/queryAllCommodity")
    @ResponseBody
    public Page<Commodity> queryAllCommodity(Integer currentPage){
        if (currentPage == null || currentPage < 0){
            currentPage = 0;
        }
        Pageable pageable = new PageRequest(currentPage, 8, Sort.Direction.DESC,"id");
        return commodityService.findAll(pageable);
    }

}
