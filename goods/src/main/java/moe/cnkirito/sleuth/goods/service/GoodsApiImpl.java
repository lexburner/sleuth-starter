package moe.cnkirito.sleuth.goods.service;

import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import moe.cnkirito.sleuth.api.GoodsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xujingfeng on 2017/11/7.
 */
@MotanService
public class GoodsApiImpl implements GoodsApi {

    Logger logger = LoggerFactory.getLogger(GoodsApiImpl.class);

    @Override
    public String getGoodsList() {
        logger.info("GoodsApi invoking ...");
        return "success";
    }
}
