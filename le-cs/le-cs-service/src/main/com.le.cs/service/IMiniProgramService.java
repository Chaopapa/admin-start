package com.le.cs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.MiniProgram;
import com.baomidou.mybatisplus.extension.service.IService;
import com.le.core.rest.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author WXY
 * @since 2019-05-10
 */
public interface IMiniProgramService extends IService<MiniProgram> {

    /**
     * 后台分页
     *
     * @param pagination 分页参数
     * @param search     搜索条件
     * @return
     */
    R findPage(Page<MiniProgram> pagination, MiniProgram search);

    /**
     * 添加或修改
     *
     * @param miniProgram 数据实体
     * @return
     */
    R editData(MiniProgram miniProgram);
}
