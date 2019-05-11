package com.le.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.OpenUser;
import com.le.cs.mapper.OpenUserMapper;
import com.le.cs.service.IOpenUserService;
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
public class OpenUserServiceImpl extends ServiceImpl<OpenUserMapper, OpenUser> implements IOpenUserService {

    @Override
    public R findPage(Page<OpenUser> pagination, OpenUser search) {
        QueryWrapper<OpenUser> qw = new QueryWrapper<>();

        IPage<OpenUser> page = baseMapper.selectPage(pagination, qw);

        return R.success(page);
    }

    public R editData(OpenUser openUser) {
        saveOrUpdate(openUser);
        return R.success();
    }
}
