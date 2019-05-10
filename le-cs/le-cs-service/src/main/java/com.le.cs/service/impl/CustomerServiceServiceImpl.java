package com.le.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.CustomerService;
import com.le.cs.mapper.CustomerServiceMapper;
import com.le.cs.service.ICustomerServiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.core.rest.R;
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

        if(page == null){
            return R.empty();
        }
        return R.success(page);
    }

    public R editData(CustomerService customerService) {
        saveOrUpdate(customerService);
        return R.success();
    }
}
