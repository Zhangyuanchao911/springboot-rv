package com.xdd.springrvmp.tourl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xdd.springrvmp.rv.dao.BizBrandDao;
import com.xdd.springrvmp.rv.dao.SysDictDao;
import com.xdd.springrvmp.rv.dao.SysRoleDao;
import com.xdd.springrvmp.rv.model.BizBrand;
import com.xdd.springrvmp.rv.model.SysDict;
import com.xdd.springrvmp.rv.model.SysRole;
import com.xdd.springrvmp.rv.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * view 跳转controller
 *
 * @Author AixLeft
 * Date 2021/1/19
 */
@Controller
public class ViewController {
    private Logger logger = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysDictDao sysDictDao;

    @Autowired
    private BizBrandDao bizBrandDao;
    /**
     * 主页
     *
     * @return
     */
    @RequestMapping("/bootm")
    public String toBootm() {
        return "bootm";
    }


    /**
     * 车辆新增
     * @return
     */
    @RequestMapping("/view/car/car_add")
    public String toViewCarAdd(Model model) {
        List<SysDict> typeList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id","type"));
        List<SysDict> gearList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id","gear"));
        List<BizBrand> brandList = bizBrandDao.selectList(new QueryWrapper<BizBrand>().eq("is_deleted",false));
        model.addAttribute("typeList",typeList);
        model.addAttribute("gearList",gearList);
        model.addAttribute("brandList",brandList);
        return "view/car/car_add";
    }

    @RequestMapping("/view/user/user_modify")
    public String toViewUser(@RequestParam(value = "user") SysUser user, Model model) {
        model.addAttribute("user", user);
        return "view/user/user_modify";
    }



    /**
     * 跳转到新增用户视图 并将需要的数据传过去
     *
     * @return
     */
    @RequestMapping("/view/user/user_add")
    public String toViewUserAdd(Model model) {
        List<SysRole> roleList = sysRoleDao.selectList(new QueryWrapper<SysRole>().eq("is_deleted", false));
        model.addAttribute("rolelist", roleList);
        return "view/user/user_add";
    }
}
