package com.le.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.MiniProgram;
import com.le.cs.mapper.MiniProgramMapper;
import com.le.cs.service.IMiniProgramService;
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
public class MiniProgramServiceImpl extends ServiceImpl<MiniProgramMapper, MiniProgram> implements IMiniProgramService {

    @Override
    public R findPage(Page<MiniProgram> pagination, MiniProgram search) {
        QueryWrapper<MiniProgram> qw = new QueryWrapper<>();

        IPage<MiniProgram> page = baseMapper.selectPage(pagination, qw);

        if(page == null){
            return R.empty();
        }
        return R.success(page);
    }

    public R editData(MiniProgram miniProgram) {
        saveOrUpdate(miniProgram);
        return R.success();
    }
}
