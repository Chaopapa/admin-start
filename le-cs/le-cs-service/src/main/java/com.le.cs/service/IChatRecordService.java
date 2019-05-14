package com.le.cs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.ChatRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.le.core.rest.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author WXY
 * @since 2019-05-14
 */
public interface IChatRecordService extends IService<ChatRecord> {

    /**
     * 后台分页
     *
     * @param pagination 分页参数
     * @param search     搜索条件
     * @return
     */
    R findPage(Page<ChatRecord> pagination, ChatRecord search);

    /**
     * 添加或修改
     *
     * @param chatRecord 数据实体
     * @return
     */
    R editData(ChatRecord chatRecord);
}
