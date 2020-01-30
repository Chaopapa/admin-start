package com.le.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.WechatMp;
import com.le.cs.mapper.WechatMpMapper;
import com.le.cs.service.IWechatMpService;
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
 * @since 2019-05-13
 */
@Service
public class WechatMpServiceImpl extends ServiceImpl<WechatMpMapper, WechatMp> implements IWechatMpService {

    @Override
    public R findPage(Page<WechatMp> pagination, WechatMp search) {
        QueryWrapper<WechatMp> qw = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(search.getName())) {
            qw.like("name",search.getName());
        }
        IPage<WechatMp> page = baseMapper.selectPage(pagination, qw);
        return R.success(page);
    }

    public R editData(WechatMp wechatMp) {
        saveOrUpdate(wechatMp);
        return R.success();
    }
}
