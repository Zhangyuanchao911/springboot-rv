package com.xdd.springrvmp.tourl;

import com.xdd.springrvmp.rv.model.vo.ShowCar;
import com.xdd.springrvmp.rv.model.vo.ShowOrderData;
import com.xdd.springrvmp.rv.service.BizCarService;
import com.xdd.springrvmp.rv.service.BizOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author AixLeft
 * Date 2021/3/20
 */
@Controller
@RequestMapping("/show")
public class DataViewController {

    @Autowired
    private BizCarService bizCarService;
    @Autowired
    private BizOrderService bizOrderService;

    @Cacheable(value = "carList", key = "'carResult'")
    @RequestMapping("/cartype")
    @ResponseBody
    public List<ShowCar> showCarType() {
        List<ShowCar> carList = bizCarService.selectShowCar();
        return carList;
    }

    @Cacheable(value = "orderList", key = "'orderResult'")
    @RequestMapping("/order")
    @ResponseBody
    public List<ShowOrderData> showOrder() {
        Map<String, int[]> order = bizOrderService.showOrder();
        List<ShowOrderData> list = new ArrayList<>();

        for (Map.Entry<String, int[]> entry : order.entrySet()
        ) {
            ShowOrderData data = new ShowOrderData();
            data.setName(entry.getKey());
            data.setType("line");
            data.setStack("总量");
            data.setData(entry.getValue());
            list.add(data);
        }
        return list;
    }

}
