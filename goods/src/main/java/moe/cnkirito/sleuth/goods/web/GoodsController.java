package moe.cnkirito.sleuth.goods.web;

import moe.cnkirito.sleuth.goods.web.model.MainOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by xujingfeng on 2017/11/7.
 */
@RestController
public class GoodsController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("test")
    public MainOrder test(){
        ResponseEntity<MainOrder> mainOrderResponseEntity = restTemplate.getForEntity("http://localhost:8060/api/order/1144", MainOrder.class);
        MainOrder body = mainOrderResponseEntity.getBody();
        return body;
    }

}
