package com.le.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.Customer;
import com.le.cs.mapper.CustomerMapper;
import com.le.cs.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.core.rest.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WXY
 * @since 2019-05-14
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Override
    public R findPage(Page<Customer> pagination, Customer search) {
        QueryWrapper<Customer> qw = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(search.getNickName())) {
            qw.like("nick_name",search.getNickName());
        }
        IPage<Customer> page = baseMapper.selectPage(pagination, qw);
        return R.success(page);
    }

    public R editData(Customer customer) {
        saveOrUpdate(customer);
        return R.success();
    }
}
