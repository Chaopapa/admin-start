package com.le.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.system.entity.SysToken;
import com.le.system.entity.enums.TokenType;
import com.le.system.mapper.SysTokenMapper;
import com.le.system.service.ISysTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @ClassName SysTokenServiceImpl
 * @Author lz
 * @Description 用户Token实现层
 * @Date 2018/10/9 11:33
 * @Version V1.0
 **/
@Slf4j
@Service
public class SysTokenServiceImpl extends ServiceImpl<SysTokenMapper, SysToken> implements ISysTokenService {
    private static final String CACHE_KEY = "sso:token";

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

    @EventListener(RedisKeyExpiredEvent.class)
    public void onRedisKeyExpiredEvent(RedisKeyExpiredEvent redisKeyExpiredEvent) {
//        String key = (String) redisTemplate.getKeySerializer().deserialize(redisKeyExpiredEvent.getSource());
//        log.debug("redis key expire：{}", key);
//
//        if (key.startsWith(CACHE_KEY)) {
//            String token = StringUtils.substringAfterLast(key, "::");
//            baseMapper.deleteById(token);
//        }
    }

    //    @Cacheable(cacheNames = CACHE_KEY, unless = "#result == null")
    @Override
    public SysToken findToken(String token) {
//        BoundValueOperations<String, Object> operations = redisTemplate.boundValueOps(CACHE_KEY + "::" + token);
//        SysToken sysToken = (SysToken) operations.get();
//
//        if (sysToken != null) {
//            TokenType tokenType = sysToken.getTokenType();
//
//            if (tokenType.getExpire() > 0) {
//                LocalDateTime expireTime = LocalDateTime.now().plus(tokenType.getExpire(), tokenType.getTemporalUnit());
//                sysToken.setExpireTime(expireTime);
//                Boolean present = operations.setIfPresent(sysToken, tokenType.getExpire(), tokenType.getTimeUnit());
//                return present != null && present ? sysToken : null;
//            } else {
//                return sysToken;
//            }
//        }

        return null;
    }

    @Override
    public SysToken createToken(Long userId, TokenType tokenType) {
        // 用户token
//        LambdaQueryWrapper<SysToken> qw = new QueryWrapper<SysToken>().lambda().eq(SysToken::getUserId, userId);
//        List<SysToken> list = this.list(qw);
//        已登录的下线
//        if (list == null || list.isEmpty()) {
//            for (SysToken sysToken : list) {
//                ISysTokenService that = (ISysTokenService) AopContext.currentProxy();
//                that.removeToken(sysToken.getToken());
//            }
//        }

//        String tokenId = generateToken();
//
//        SysToken token = new SysToken();
//        token.setId(tokenId);
//        token.setUserId(userId);
//        token.setTokenType(tokenType);
//
//        if (tokenType.getExpire() > 0) {
//            LocalDateTime expireTime = LocalDateTime.now().plus(tokenType.getExpire(), tokenType.getTemporalUnit());
//            token.setExpireTime(expireTime);
//        }
//
//        baseMapper.insert(token);
//        BoundValueOperations<String, Object> operations = redisTemplate.boundValueOps(CACHE_KEY + "::" + tokenId);
//
//        if (tokenType.getExpire() > 0) {
//            operations.set(token, tokenType.getExpire(), tokenType.getTimeUnit());
//        } else {
//            operations.set(token);
//        }

//        return token;
        return null;
    }

//    @Override
//    public void expireToken(Long userId) {
//
////        // 用户token
////        SysToken tokenEntity = new SysToken();
////        // 设置用户id
////        tokenEntity.setUserId(userId);
////        // 设置过期时间
////        tokenEntity.setExpireTime( LocalDateTime.now());
////        // 更新或保存toke
////        this.saveOrUpdate(tokenEntity);
//    }

    /**
     * 创建token
     *
     * @return 返回32位长度uuid
     */
    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @CacheEvict(cacheNames = CACHE_KEY)
    @Override
    public void removeToken(String token) {
        baseMapper.deleteById(token);
    }
}
