package com.le.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.AppCuster;
import com.le.cs.mapper.AppCusterMapper;
import com.le.cs.service.IAppCusterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.core.rest.R;
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
public class AppCusterServiceImpl extends ServiceImpl<AppCusterMapper, AppCuster> implements IAppCusterService {

    @Override
    public R findPage(Page<AppCuster> pagination, AppCuster search) {
        QueryWrapper<AppCuster> qw = new QueryWrapper<>();

        IPage<AppCuster> page = baseMapper.selectPage(pagination, qw);
        return R.success(page);
    }

    public R editData(AppCuster appCuster) {
        saveOrUpdate(appCuster);
        return R.success();
    }
}
