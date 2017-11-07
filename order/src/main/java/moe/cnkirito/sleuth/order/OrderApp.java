package moe.cnkirito.sleuth.order;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by xujingfeng on 2017/11/7.
 */
@SpringBootApplication
public class OrderApp {

    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class, args);
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    }

}
