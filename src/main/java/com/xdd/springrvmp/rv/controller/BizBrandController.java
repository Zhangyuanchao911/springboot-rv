package com.xdd.springrvmp.rv.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.dao.BizBrandDao;
import com.xdd.springrvmp.rv.model.BizBrand;
import com.xdd.springrvmp.rv.service.BizBrandService;
import com.xdd.springrvmp.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 汽车品牌
 * </p>
 *
 * @author AixLeft
 * @since 2021-01-18
 */
@Controller
@RequestMapping("/rv/biz-brand")
public class BizBrandController {
    private Logger logger = LoggerFactory.getLogger(BizBrandController.class);
    @Autowired
    private BizBrandDao bizBrandDao;

    @Autowired
    private BizBrandService bizBrandService;

    @RequestMapping("/list")
    public String getList(Model model,@RequestParam(value = "success", required = false) String success,
                          @RequestParam(value = "fail", required = false) String fail,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {

        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        IPage<BizBrand> page = bizBrandService.selectByPage(new Page<>(pageNum, pageSize), null);
        List<BizBrand> brandList = page.getRecords();
        List<Integer> list = new ArrayList<>();
        int i = (int) page.getPages();
        for (int j = 1; j <= i; j++) {
            list.add(j);
        }
        model.addAttribute("success",success);
        model.addAttribute("fail",fail);
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", page);
        model.addAttribute("brands", brandList);
        return "view/brand/brand_list";
    }

    @RequestMapping("/fuzzylist")
    public String fuzzylist(String kw, Model model,
                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {

        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        IPage<BizBrand> page = bizBrandService.selectByPage(new Page<>(pageNum, pageSize), kw);
        List<BizBrand> brandList = page.getRecords();
        List<Integer> list = new ArrayList<>();
        int i = (int) page.getPages();
        for (int j = 1; j <= i; j++) {
            list.add(j);
        }
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", page);
        model.addAttribute("brands", brandList);
        return "view/brand/brand_list";
    }

    @ResponseBody
    @RequestMapping("/checkBrandName")
    public String checkBrand(String brandName) {
        JSONObject result = new JSONObject();
        Integer count = bizBrandDao.selectCount(new QueryWrapper<BizBrand>().eq("brand_name", brandName).eq("is_deleted",false));
        if (count != null && count != 0) {
            result.put("valid", false);
        } else {
            result.put("valid", true);
        }
        return result.toJSONString();
    }

    @PostMapping("/add")
    public String add(BizBrand bizBrand, RedirectAttributes attributes) {
        logger.info("从前端得到bizBrand---->" + bizBrand);
        bizBrand.setBrandId(UUIDUtils.getUUId());
        bizBrand.setCreatTime(new Date());
        bizBrand.setUpdateTime(new Date());
        bizBrand.setIsDeleted(false);
        int flag = bizBrandDao.insert(bizBrand);
        if (flag == 1) {
            attributes.addAttribute("success","新增成功");
            return "redirect:/rv/biz-brand/list";
        } else {
            attributes.addAttribute("fail","新增失败");
            return "redirect:/rv/biz-brand/list";
        }
    }

    /**
     * 用id查询
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/findbyid")
    public String findbyid(String id, Model model) {
        BizBrand bizBrand = bizBrandDao.selectByPrimaryKey(id);
        model.addAttribute("brand", bizBrand);
        return "view/brand/brand_modify";
    }

    @RequestMapping("/update")
    public String update(BizBrand bizBrand, RedirectAttributes attributes) {
        bizBrand.setUpdateTime(new Date());
        int flag = bizBrandDao.updateByPrimaryKeySelective(bizBrand);
        if (flag == 1) {
            attributes.addAttribute("success","修改成功");
            return "redirect:/rv/biz-brand/list";
        } else {
            attributes.addAttribute("fail","修改失败");
            return "redirect:/rv/biz-brand/list";
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id, RedirectAttributes attributes) {
        int flag = bizBrandDao.deleteByPrimaryKey(id);
        if (flag == 1) {
            attributes.addAttribute("success","删除成功");
            return "redirect:/rv/biz-brand/list";
        } else {
            attributes.addAttribute("fail","删除失败");
            return "redirect:/rv/biz-brand/list";
        }
    }
}
