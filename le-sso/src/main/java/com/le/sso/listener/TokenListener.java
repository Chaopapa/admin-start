//package com.le.sso.listener;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.stereotype.Component;
//
///**
// * @author 严秋旺
// * @since 2019-05-05 10:27
// **/
//@Slf4j
//@Component
//public class TokenListener extends KeyExpirationEventMessageListener {
//
//
//    public TokenListener(RedisMessageListenerContainer listenerContainer) {
//        super(listenerContainer);
//    }
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        super.onMessage(message, pattern);
//    }
//}
