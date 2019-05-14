package com.le.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.CustomerService;
import com.le.cs.entity.OpenUser;
import com.le.cs.mapper.OpenUserMapper;
import com.le.cs.service.IOpenUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.core.rest.R;
import org.apache.commons.lang3.StringUtils;
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
public class OpenUserServiceImpl extends ServiceImpl<OpenUserMapper, OpenUser> implements IOpenUserService {

    @Override
    public R findPage(Page<OpenUser> pagination, OpenUser search) {
        QueryWrapper<OpenUser> qw = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(search.getName())) {
            qw.like("name",search.getName());
        }
        if (StringUtils.isNotEmpty(search.getUsername())) {
            qw.like("username",search.getUsername());
        }
        IPage<OpenUser> page = baseMapper.selectPage(pagination, qw);

        return R.success(page);
    }

    public R editData(OpenUser openUser) {
        saveOrUpdate(openUser);
        return R.success();
    }

    @Override
    public boolean usernameExists(Long id, String username) {
        LambdaQueryWrapper<OpenUser> wrapper = new LambdaQueryWrapper<>();

        if (id != null) {
            wrapper.ne(OpenUser::getId, id);
        }

        wrapper.eq(OpenUser::getUsername, username);
        Integer count = baseMapper.selectCount(wrapper);
        return count != null && count > 0;
    }
}
