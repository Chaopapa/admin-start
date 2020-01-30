package com.le.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.le.system.entity.SysToken;
import com.le.system.entity.enums.TokenType;

/**
 * @ClassName ISysTokenService
 * @Author lz
 * @Description 用户Token接口层
 * @Date 2018/10/9 11:32
 * @Version V1.0
 **/
public interface ISysTokenService extends IService<SysToken> {

    /**
     * @param token 值
     * @return com.le.core.entity.SysToken
     * @description token查询用户Token
     * @author lz
     * @date 2018/10/18 11:25
     * @version V1.0.0
     */
    SysToken findToken(String token);

    /**
     * 用户id创建Token
     *
     * @param userId    用户id
     * @param tokenType token类型
     * @return com.le.core.entity.SysToken
     */
    SysToken createToken(Long userId, TokenType tokenType);

//    /**
//     * @param userId
//     * @return com.le.core.entity.SysToken
//     * @description 用户id
//     * @author lz
//     * @date 2018/10/18 11:25
//     * @version V1.0.0
//     */
//    void expireToken(Long userId);

    /**
     * 删除token
     *
     * @param token
     */
    void removeToken(String token);
}
