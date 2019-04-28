package com.le.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.system.entity.SysToken;
import com.le.system.mapper.SysTokenMapper;
import com.le.system.service.ISysTokenService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @ClassName SysTokenServiceImpl
 * @Author lz
 * @Description 用户Token实现层
 * @Date 2018/10/9 11:33
 * @Version V1.0
 **/
@Service
public class SysTokenServiceImpl extends ServiceImpl<SysTokenMapper, SysToken> implements ISysTokenService {

    /**
     * 24小时后过期
     */
    private final static int EXPIRE = 3600 * 24;

    @Cacheable(cacheNames = "token", unless = "#result == null")
    @Override
    public SysToken findToken(String token) {
        LambdaQueryWrapper<SysToken> qw = new QueryWrapper<SysToken>().lambda().eq(SysToken::getToken, token);
        return this.getOne(qw);
    }

    @Override
    public SysToken createToken(Long userId) {
        // 当前时间
        Date now = new Date();
        // 过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        // 生成token
        String token = generateToken();

        // 用户token
        LambdaQueryWrapper<SysToken> qw = new QueryWrapper<SysToken>().lambda().eq(SysToken::getUserId, userId);
        SysToken tokenEntity = this.getOne(qw);
        if (tokenEntity == null) {
            tokenEntity = new SysToken();
        }
        // 设置用户id
        tokenEntity.setUserId(userId);
        // 设置token
        tokenEntity.setToken(token);
        // 设置过期时间
        // tokenEntity.setExpireTime(expireTime);
        // 更新或保存token
        this.saveOrUpdate(tokenEntity);

        return tokenEntity;
    }

    @Override
    public void expireToken(Long userId) {
        // 当前时间
        Date now = new Date();
        // 用户token
        SysToken tokenEntity = new SysToken();
        // 设置用户id
        tokenEntity.setUserId(userId);
        // 设置过期时间
        tokenEntity.setExpireTime(now);
        // 更新或保存toke
        this.saveOrUpdate(tokenEntity);
    }

    /**
     * @return java.lang.String
     * @description 创建token
     * @author lz
     * @date 2018/10/18 11:40
     * @version V1.0.0
     */
    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
