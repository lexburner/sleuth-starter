package moe.cnkirito.sleuth.order.web;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import moe.cnkirito.sleuth.api.GoodsApi;
import moe.cnkirito.sleuth.order.model.MainOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xujingfeng on 2017/11/7.
 */
@RestController
@RequestMapping("/api")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping("/order/{id}")
    public MainOrder getOrder(@PathVariable("id") String id) {
        logger.info("order invoking ...");
        return new MainOrder(id, new BigDecimal(200D), new Date());
    }

    @MotanReferer
    GoodsApi goodsApi;

    @RequestMapping("/goods")
    public String getGoodsList() {
        logger.info("getGoodsList invoking ...");
        return goodsApi.getGoodsList();
    }

}
