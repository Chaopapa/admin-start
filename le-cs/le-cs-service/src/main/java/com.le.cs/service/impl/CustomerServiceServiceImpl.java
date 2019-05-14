package com.le.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.CustomerService;
import com.le.cs.entity.OpenUser;
import com.le.cs.mapper.CustomerServiceMapper;
import com.le.cs.service.ICustomerServiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.core.rest.R;
import com.le.cs.service.IOpenUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author WXY
 * @since 2019-05-10
 */
@Service
public class CustomerServiceServiceImpl extends ServiceImpl<CustomerServiceMapper, CustomerService> implements ICustomerServiceService {

    @Autowired
    private IOpenUserService openUserService;

    @Override
    public R findPage(Page<CustomerService> pagination, CustomerService search) {
        QueryWrapper<CustomerService> qw = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(search.getName())) {
            qw.like("name", search.getName());
        }
        if (StringUtils.isNotEmpty(search.getUsername())) {
            qw.like("username", search.getUsername());
        }
        IPage<CustomerService> page = baseMapper.selectPage(pagination, qw);

        return R.success(page);
    }

    public R editData(CustomerService customerService) {
        String password = customerService.getPassword();
        Long id = customerService.getId();
        if (id == null && StringUtils.isEmpty(password)) {
            return R.error("请输入密码");
        }
        if (id == null) {
            save(customerService);
            customerService.setPassword(DigestUtils.md5Hex(password + customerService.getId()));
        } else {
            CustomerService customer = getById(customerService.getId());
            if (StringUtils.isNotEmpty(password)) {
                customerService.setPassword(DigestUtils.md5Hex(password + customerService.getId()));
            } else {
                customerService.setPassword(customer.getPassword());
            }
        }
        updateById(customerService);
        return R.success();
    }

    @Override
    public boolean usernameExists(Long id, String username) {
        LambdaQueryWrapper<CustomerService> wrapper = new LambdaQueryWrapper<>();

        if (id != null) {
            wrapper.ne(CustomerService::getId, id);
        }

        wrapper.eq(CustomerService::getUsername, username);
        Integer count = baseMapper.selectCount(wrapper);
        return count != null && count > 0;
    }

    @Override
    public boolean customerNum(Long openUserId, Long id) {
        CustomerService customerService = getById(id);
        if (customerService != null) {
            if (customerService.getOpenUserId() == openUserId) {
                return true;
            }
        }
        LambdaQueryWrapper<CustomerService> eq = new QueryWrapper<CustomerService>().lambda().eq(CustomerService::getOpenUserId, openUserId);
        Integer count = count(eq);//已有的客服数
        OpenUser openUser = openUserService.getById(openUserId);
        if (openUser != null) {
            Integer maxCsCount = openUser.getMaxCsCount();//设定的客服上线
            if (maxCsCount != null) {
                return count < maxCsCount;
            }
        }
        return false;
    }
}
