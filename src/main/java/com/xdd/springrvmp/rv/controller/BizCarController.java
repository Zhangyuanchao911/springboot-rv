package com.xdd.springrvmp.rv.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.springrvmp.rv.dao.BizBrandDao;
import com.xdd.springrvmp.rv.dao.BizCarDao;
import com.xdd.springrvmp.rv.dao.SysDictDao;
import com.xdd.springrvmp.rv.model.BizBrand;
import com.xdd.springrvmp.rv.model.BizCar;
import com.xdd.springrvmp.rv.model.SysDict;
import com.xdd.springrvmp.rv.model.vo.Car;
import com.xdd.springrvmp.rv.service.BizCarService;
import com.xdd.springrvmp.utils.Constant;
import com.xdd.springrvmp.utils.ImportExcel;
import com.xdd.springrvmp.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 车辆Controller
 * </p>
 *
 * @author AixLeft
 * @since 2021-01-18
 */
@Controller
@RequestMapping("/rv/biz-car")
public class BizCarController {
    private Logger logger = LoggerFactory.getLogger(BizCarController.class);
    @Autowired
    private BizCarDao bizCarDao;

    @Autowired
    private BizCarService bizCarService;

    @Autowired
    private SysDictDao sysDictDao;

    @Autowired
    private BizBrandDao bizBrandDao;

    /**
     * 分页查询
     *
     * @param model
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/listbypage")
    public String listByPage(Model model,
                             @RequestParam(value = "success", required = false) String success,
                             @RequestParam(value = "fail", required = false) String fail,
                             @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        IPage<BizCar> page = bizCarService.selectPage(new Page<>(pageNum, pageSize), null, null);
        List<BizCar> carList = page.getRecords();
        List<BizBrand> bizBrandList = bizBrandDao.selectList(new QueryWrapper<BizBrand>().eq("is_deleted", false));
        List<SysDict> typeList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "type"));
        List<SysDict> gearList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "gear"));
        List<Car> carVo = new ArrayList<>();

        for (BizCar car : carList) {
            Car car1 = new Car();
            car1.setBizCar(car);
            car1.setSysDictType(sysDictDao.selectByPrimaryKey(car.getType()));
            car1.setSysDictGear(sysDictDao.selectByPrimaryKey(car.getGear()));
            car1.setBizBrand(bizBrandDao.selectByPrimaryKey(car.getBrandId()));
            carVo.add(car1);
        }
        List<Integer> list = new ArrayList<>();
        int i = (int) page.getPages();
        for (int j = 1; j <= i; j++) {
            list.add(j);
        }
        model.addAttribute("success", success);
        model.addAttribute("fail", fail);
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", page);
        model.addAttribute("carList", carVo);

        return "view/car/car_list";

    }

    /**
     * 租赁时  按照房车类型查询
     *
     * @param typeId
     * @param model
     * @return
     */
    @RequestMapping("/listbytypestate")
    public String findByTypeState(@RequestParam(value = "typeId") String typeId, Model model,
                                  @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        IPage<BizCar> page = bizCarService.selectPageSave(new Page<>(pageNum, pageSize), null, typeId, null);
        List<BizCar> carList = page.getRecords();
        List<BizBrand> bizBrandList = bizBrandDao.selectList(new QueryWrapper<BizBrand>().eq("is_deleted", false));
        List<SysDict> typeList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "type"));
        List<SysDict> gearList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "gear"));
        List<Car> carVo = new ArrayList<>();

        for (BizCar car : carList) {
            Car car1 = new Car();
            car1.setBizCar(car);
            car1.setSysDictType(sysDictDao.selectByPrimaryKey(car.getType()));
            car1.setSysDictGear(sysDictDao.selectByPrimaryKey(car.getGear()));
            car1.setBizBrand(bizBrandDao.selectByPrimaryKey(car.getBrandId()));
            carVo.add(car1);
        }
        List<Integer> list = new ArrayList<>();
        int i = (int) page.getPages();
        for (int j = 1; j <= i; j++) {
            list.add(j);
        }
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", page);
        model.addAttribute("carList", carVo);
        model.addAttribute("types", typeList);
        model.addAttribute("gears", gearList);

        return "view/sale/sale_list";
    }

    /**
     * 租赁时  按照档类查询
     *
     * @param gearId
     * @param model
     * @return
     */
    @RequestMapping("/listbygearstate")
    public String findByGearState(@RequestParam(value = "gearId") String gearId, Model model,
                                  @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        IPage<BizCar> page = bizCarService.selectPageSave(new Page<>(pageNum, pageSize), null, null, gearId);
        List<BizCar> carList = page.getRecords();
        List<BizBrand> bizBrandList = bizBrandDao.selectList(new QueryWrapper<BizBrand>().eq("is_deleted", false));
        List<SysDict> typeList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "type"));
        List<SysDict> gearList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "gear"));
        List<Car> carVo = new ArrayList<>();

        for (BizCar car : carList) {
            Car car1 = new Car();
            car1.setBizCar(car);
            car1.setSysDictType(sysDictDao.selectByPrimaryKey(car.getType()));
            car1.setSysDictGear(sysDictDao.selectByPrimaryKey(car.getGear()));
            car1.setBizBrand(bizBrandDao.selectByPrimaryKey(car.getBrandId()));
            carVo.add(car1);
        }
        List<Integer> list = new ArrayList<>();
        int i = (int) page.getPages();
        for (int j = 1; j <= i; j++) {
            list.add(j);
        }
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", page);
        model.addAttribute("carList", carVo);
        model.addAttribute("types", typeList);
        model.addAttribute("gears", gearList);

        return "view/sale/sale_list";
    }


    /**
     * 租赁时 通过id查询车辆信息
     *
     * @param carId
     * @return
     */
    @RequestMapping("/salefindbyid")
    public String salefindById(@RequestParam(value = "id") String id, Model model) {
        BizCar bizCar = bizCarDao.selectOne(new QueryWrapper<BizCar>().eq("car_id", id));
        model.addAttribute("car", bizCar);
        return "view/order/order_add";
    }

    /**
     * 租赁时查询车辆
     *
     * @param model
     * @return
     */
    @RequestMapping("/salelist")
    public String salelist(Model model,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {

        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        IPage<BizCar> page = bizCarService.selectPageSave(new Page<>(pageNum, pageSize), null, null, null);
        List<BizCar> carList = page.getRecords();
        List<BizBrand> bizBrandList = bizBrandDao.selectList(new QueryWrapper<BizBrand>().eq("is_deleted", false));
        List<SysDict> typeList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "type"));
        List<SysDict> gearList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "gear"));
        List<Car> carVo = new ArrayList<>();

        for (BizCar car : carList) {
            Car car1 = new Car();
            car1.setBizCar(car);
            car1.setSysDictType(sysDictDao.selectByPrimaryKey(car.getType()));
            car1.setSysDictGear(sysDictDao.selectByPrimaryKey(car.getGear()));
            car1.setBizBrand(bizBrandDao.selectByPrimaryKey(car.getBrandId()));

            carVo.add(car1);
        }
        List<Integer> list = new ArrayList<>();
        int i = (int) page.getPages();
        for (int j = 1; j <= i; j++) {
            list.add(j);
        }
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", page);
        model.addAttribute("carList", carVo);
        model.addAttribute("types", typeList);
        model.addAttribute("gears", gearList);
        return "view/sale/sale_list";
    }

    /**
     * 租赁时 模糊查询
     *
     * @param kw
     * @param model
     * @return
     */
    @RequestMapping("/savefuzzylist")
    public String savefuzzyList(@RequestParam(value = "kw") String kw, Model model,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {

        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        IPage<BizCar> page = bizCarService.selectPageSave(new Page<>(pageNum, pageSize), kw, null, null);
        List<BizCar> carList = page.getRecords();
        List<BizBrand> bizBrandList = bizBrandDao.selectList(new QueryWrapper<BizBrand>().eq("is_deleted", false));
        List<SysDict> typeList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "type"));
        List<SysDict> gearList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "gear"));
        List<Car> carVo = new ArrayList<>();

        for (BizCar car : carList) {
            Car car1 = new Car();
            car1.setBizCar(car);
            car1.setSysDictType(sysDictDao.selectByPrimaryKey(car.getType()));
            car1.setSysDictGear(sysDictDao.selectByPrimaryKey(car.getGear()));
            car1.setBizBrand(bizBrandDao.selectByPrimaryKey(car.getBrandId()));
            carVo.add(car1);
        }
        List<Integer> list = new ArrayList<>();
        int i = (int) page.getPages();
        for (int j = 1; j <= i; j++) {
            list.add(j);
        }
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", page);
        model.addAttribute("carList", carVo);
        model.addAttribute("types", typeList);
        model.addAttribute("gears", gearList);
        return "view/sale/sale_list";
    }


    /**
     * 验证车牌号是否存在
     *
     * @param carNumber
     * @return
     */
    @RequestMapping("/checkcarnumber")
    @ResponseBody
    public String checkCarName(String carNumber) {
        JSONObject result = new JSONObject();
        Integer count = bizCarDao.selectCount(new QueryWrapper<BizCar>().eq("is_deleted", false).eq("car_number", carNumber));
        if (count != null && count != 0) {
            result.put("valid", false);
        } else {
            result.put("valid", true);
        }
        return result.toJSONString();

    }


    /**
     * 模糊查询 车辆
     *
     * @param kw
     * @param model
     * @return
     */
    @RequestMapping("/fuzzylist")
    public String fuzzyList(@RequestParam(value = "kw") String kw, Model model,
                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        IPage<BizCar> page = bizCarService.selectPage(new Page<>(pageNum, pageSize), kw, null);
        List<BizCar> carList = page.getRecords();
        List<BizBrand> bizBrandList = bizBrandDao.selectList(new QueryWrapper<BizBrand>().eq("is_deleted", false));
        List<SysDict> typeList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "type"));
        List<SysDict> gearList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "gear"));
        List<Car> carVo = new ArrayList<>();

        for (BizCar car : carList) {
            Car car1 = new Car();
            car1.setBizCar(car);
            car1.setSysDictType(sysDictDao.selectByPrimaryKey(car.getType()));
            car1.setSysDictGear(sysDictDao.selectByPrimaryKey(car.getGear()));
            car1.setBizBrand(bizBrandDao.selectByPrimaryKey(car.getBrandId()));
            carVo.add(car1);
        }
        List<Integer> list = new ArrayList<>();
        int i = (int) page.getPages();
        for (int j = 1; j <= i; j++) {
            list.add(j);
        }
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", page);
        model.addAttribute("carList", carVo);

        return "view/car/car_list";
    }


    /**
     * 通过租赁状态 查询车辆列表
     *
     * @param state
     * @param model
     * @return
     */
    @RequestMapping("/listbystate")
    public String findByState(@RequestParam(value = "state") String state, Model model,
                              @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        logger.info("获取到租赁状态:" + state);
        Integer pageSize = 10;
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        IPage<BizCar> page = bizCarService.selectPage(new Page<>(pageNum, pageSize), null, state);
        List<BizCar> carList = page.getRecords();
        List<BizBrand> bizBrandList = bizBrandDao.selectList(new QueryWrapper<BizBrand>().eq("is_deleted", false));
        List<SysDict> typeList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "type"));
        List<SysDict> gearList = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "gear"));
        List<Car> carVo = new ArrayList<>();

        for (BizCar car : carList) {
            Car car1 = new Car();
            car1.setBizCar(car);
            car1.setSysDictType(sysDictDao.selectByPrimaryKey(car.getType()));
            car1.setSysDictGear(sysDictDao.selectByPrimaryKey(car.getGear()));
            car1.setBizBrand(bizBrandDao.selectByPrimaryKey(car.getBrandId()));
            carVo.add(car1);
        }
        List<Integer> list = new ArrayList<>();
        int i = (int) page.getPages();
        for (int j = 1; j <= i; j++) {
            list.add(j);
        }
        model.addAttribute("list", list);
        model.addAttribute("pageInfo", page);
        model.addAttribute("carList", carVo);

        return "view/car/car_list";
    }


    /**
     * 通过id查询车辆信息
     *
     * @param carId
     * @return
     */
    @RequestMapping("/findbyid")
    public String findById(@RequestParam(value = "id") String id, Model model) {
        BizCar bizCar = bizCarDao.selectOne(new QueryWrapper<BizCar>().eq("car_id", id));
        String type = bizCar.getType();
        String gear = bizCar.getGear();
        String brandId = bizCar.getBrandId();
        List<SysDict> sysDictType = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "type"));
        List<SysDict> sysDictGear = sysDictDao.selectList(new QueryWrapper<SysDict>().eq("type_id", "gear"));
        List<BizBrand> bizBrands = bizBrandDao.selectList(new QueryWrapper<BizBrand>().eq("is_deleted", false));

        model.addAttribute("car", bizCar);
        model.addAttribute("tepes", sysDictType);
        model.addAttribute("gears", sysDictGear);
        model.addAttribute("brands", bizBrands);
        return "view/car/car_modify";
    }


    /**
     * 添加车辆信息
     *
     * @param bizCar
     * @return
     */
    @RequestMapping("/add")
    public String addCar(BizCar bizCar, RedirectAttributes attributes) {
        bizCar.setCarId(UUIDUtils.getUUId());
        bizCar.setCreatTime(new Date());
        bizCar.setUpdateTime(new Date());
        bizCar.setIsDeleted(false);
        bizCar.setState(Constant.LEASED_NO);
        logger.info("新增车辆信息" + bizCar.toString());
        BizCar car = bizCarDao.selectOne(new QueryWrapper<BizCar>().eq("car_number", bizCar.getCarNumber()));
        if (car == null || !car.getCarNumber().equals(bizCar.getCarNumber())) {
            bizCarService.addSelected(bizCar);
            attributes.addAttribute("success", "新增成功");
            return "redirect:/rv/biz-car/listbypage";
        } else {
            attributes.addAttribute("success", "新增失败");
            return "redirect:/rv/biz-car/listbypage";
        }
    }

    /**
     * 更新车辆信息 包括状态
     *
     * @param bizCar
     * @return
     */
    @RequestMapping("/update")
    public String updateCar(BizCar bizCar, RedirectAttributes attributes) {
        bizCar.setUpdateTime(new Date());
        int flag = bizCarDao.updateByPrimaryKeySelective(bizCar);
        if (flag == 1) {
            attributes.addAttribute("success", "更新成功");
            return "redirect:/rv/biz-car/listbypage";
        } else {
            attributes.addAttribute("fail", "更新失败");
            return "redirect:/rv/biz-car/listbypage";
        }
    }

    /**
     * 逻辑删除
     *
     * @param carId
     * @return
     */

    @RequestMapping("/delete")
    public String delete(@RequestParam(value = "id") String id, RedirectAttributes attributes) {
        int flag = bizCarService.deleteByPrimaryKey(id);
        if (flag == 1) {
            attributes.addAttribute("success", "删除成功");
            return "redirect:/rv/biz-car/listbypage";
        } else {
            attributes.addAttribute("fail", "删除失败");
            return "redirect:/rv/biz-car/listbypage";

        }

    }

    /**
     * 导出excel
     */
    @GetMapping(value = "/exportListExcel")
    public void exportListExcel() {
        List<BizCar> carList = bizCarDao.selectList(new QueryWrapper<BizCar>().eq("is_deleted", false));
        List<Car> carLists = new ArrayList<>();

        for (BizCar car : carList) {
            Car car1 = new Car();
            car1.setBizCar(car);
            car1.setSysDictType(sysDictDao.selectByPrimaryKey(car.getType()));
            car1.setSysDictGear(sysDictDao.selectByPrimaryKey(car.getGear()));
            car1.setBizBrand(bizBrandDao.selectByPrimaryKey(car.getBrandId()));

            carLists.add(car1);
        }
        bizCarService.exportListExcel(carLists);
    }
}
