package com.shimh.config.kafka;

import ai.yunxi.im.common.constant.KakfaTopicConstant;
import ai.yunxi.im.common.pojo.ImRouterRequestMessage;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by zn on 2020/9/14.
 */
@Component
public class KafkaUtil {

    private static  Logger log= LoggerFactory.getLogger(KafkaUtil.class);

    @Autowired
    private ChatProducer chatProducer;

    private Gson gson = new Gson();

    public boolean sendChatKafka(@RequestBody ImRouterRequestMessage message) {
        try {
            log.info("kafka的消息={}", gson.toJson(message));
            chatProducer.send(KakfaTopicConstant.CHAT, message);
            log.info("发送kafka成功.");
            return true;
        } catch (Exception e) {
            log.error("发送kafka失败", e);
            return false;
        }
    }

}
