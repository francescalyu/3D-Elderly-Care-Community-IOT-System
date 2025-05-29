package com.francesca.mqtt;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.francesca.config.MqttConfig;
import com.francesca.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author francesca

 * 2025-05-16
 */

@Slf4j
@Component
public class PushCallback implements MqttCallback {

    private MqttPushClient client;

    private MqttConfig mqttConfiguration;

    private MqttService mqttService;

//    @Resource
//    MqttService mqttService;

    public PushCallback(MqttPushClient client, MqttConfig mqttConfiguration , MqttService mqttService) {
        this.client = client;
        this.mqttConfiguration = mqttConfiguration;
    }

    @Override
    public void connectionLost(Throwable cause) {
        /** 连接丢失后，一般在这里面进行重连 **/



        if (client != null) {
            while (true) {
                try {
                    log.info("==============》》》[MQTT] 连接丢失，尝试重连...");
                    MqttPushClient mqttPushClient = new MqttPushClient(mqttService);
                    mqttPushClient.connect(mqttConfiguration);
                    if (MqttPushClient.getClient().isConnected()) {
                        log.info("=============>>重连成功");
                    }
                    break;
                } catch (Exception e) {
                    log.error("=============>>>[MQTT] 连接断开，重连失败！<<=============");

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                    }
                }

            }
        }
        log.info(cause.getMessage());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // publish后会执行到这里
        log.info("pushComplete==============>>>" + token.isComplete());
    }

    /**
     * 监听对应的主题消息
     *
     * @param topic
     * @param message
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
        log.info("============》》接收消息主题 : " + topic);
        log.info("============》》接收消息Qos : " + message.getQos());
        log.info("============》》接收消息内容原始内容 : " + new String(message.getPayload()));

        if (ObjectUtil.isEmpty(message.getPayload()) || message.getPayload().length <= 10 ){
            log.info("============》》接收消息为空 : " );
        }else {
            mqttService.messageArrived(topic, new String(message.getPayload(), "UTF-8"));
        }



//        log.info("============》》接收消息内容GB2312 : " + new String(message.getPayload(), "GB2312"));
//        log.info("============》》接收消息内容UTF-8 : " + new String(message.getPayload(), "UTF-8"));
//        try {
//            if (topic.equals("datapoint")) {
//                MqttResponseBody mqttResponseBody = JsonUtil.toObject(new String(message.getPayload(), "UTF-8"),
//                        MqttResponseBody.class);
//                MqttService mqttService = SpringUtil.getBean(MqttServiceImpl.class);
//                mqttService.messageArrived(mqttResponseBody);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("============》》接收消息主题异常 : " + e.getMessage());
//        }



    }

}