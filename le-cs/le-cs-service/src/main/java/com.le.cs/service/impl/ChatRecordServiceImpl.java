package com.le.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.ChatRecord;
import com.le.cs.mapper.ChatRecordMapper;
import com.le.cs.service.IChatRecordService;
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
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordMapper, ChatRecord> implements IChatRecordService {

    @Override
    public R findPage(Page<ChatRecord> pagination, ChatRecord search) {
        QueryWrapper<ChatRecord> qw = new QueryWrapper<>();

        IPage<ChatRecord> page = baseMapper.selectPage(pagination, qw);
        return R.success(page);
    }

    public R editData(ChatRecord chatRecord) {
        saveOrUpdate(chatRecord);
        return R.success();
    }
}
