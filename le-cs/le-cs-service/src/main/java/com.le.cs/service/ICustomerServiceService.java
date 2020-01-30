package com.le.cs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.CustomerService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.le.core.rest.R;
import com.le.cs.entity.enums.LoginType;
import com.le.cs.vo.CustomerLoginVo;
import com.le.cs.vo.PassWordVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author WXY
 * @since 2019-05-10
 */
public interface ICustomerServiceService extends IService<CustomerService> {

    /**
     * 后台分页
     *
     * @param pagination 分页参数
     * @param search     搜索条件
     * @return
     */
    R findPage(Page<CustomerService> pagination, CustomerService search);

    /**
     * 添加或修改
     *
     * @param customerService 数据实体
     * @return
     */
    R editData(CustomerService customerService);

    /**
     * 查询客服人员信息
     *
     * @param username
     * @return
     */
    CustomerService findByUserName(String username);

    boolean usernameExists(Long id, String username);

    boolean customerNum(Long openUserId, Long id);

    /**
     * 手机验证修改密码
     *
     * @param customerLoginVo
     * @param passWordVo
     * @return
     */
    R updatePasswordValidate(CustomerLoginVo customerLoginVo, PassWordVo passWordVo);

    /**
     * 上线
     *
     * @param userId    用户ID
     * @param channelId 通道ID
     * @param loginType 登录类型
     * @param hostName  登录IP
     */
    void online(Long userId, String channelId, LoginType loginType, String hostName);

    /**
     * 下线
     *
     * @param userId 用户ID
     */
    void offline(Long userId);
}
