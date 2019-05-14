package com.le.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.CustomerService;
import com.le.cs.mapper.CustomerServiceMapper;
import com.le.cs.service.ICustomerServiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.core.rest.R;
import com.le.cs.vo.LoginServiceVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WXY
 * @since 2019-05-10
 */
@Service
public class CustomerServiceServiceImpl extends ServiceImpl<CustomerServiceMapper, CustomerService> implements ICustomerServiceService {

    @Override
    public R findPage(Page<CustomerService> pagination, CustomerService search) {
        QueryWrapper<CustomerService> qw = new QueryWrapper<>();

        IPage<CustomerService> page = baseMapper.selectPage(pagination, qw);

        return R.success(page);
    }

    public R editData(CustomerService customerService) {
        saveOrUpdate(customerService);
        return R.success();
    }

    @Override
    public R login(LoginServiceVo loginServiceVo) {
        CustomerService user = findByUserName(loginServiceVo.getUsername());
        if (user == null) {
            return R.error("登录名或密码错误");
        }

        String encrypt = encrypt(user.getId(), loginServiceVo.getPassword());
        if (!encrypt.equals(user.getPassword())) {
            return R.error("登录名或密码错误");
        }
        /*if (UserStatus.normal!=user.getStatus()){
            return R.error("该用户被禁用");
        }*/
      /*  Token token = tokenService.createToken(user.getId());
        Map<String, Object> map = new HashMap<>(2);
        map.put("token", token.getToken());*/
        return R.success();
    }

    @Override
    public CustomerService findByUserName(String username) {
        QueryWrapper<CustomerService> qw=new QueryWrapper<>();
        qw.eq("username",username);
        return baseMapper.selectOne(qw);
    }

    public String encrypt(Long id, String password) {
        if (StringUtils.isEmpty(password)) {
            return null;
        }

        return DigestUtils.md5Hex(password.toLowerCase()+id);
    }
}
