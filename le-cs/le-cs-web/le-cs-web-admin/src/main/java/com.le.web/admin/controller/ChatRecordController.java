package com.le.web.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.ChatRecord;
import com.le.cs.service.IChatRecordService;
import com.le.core.rest.R;
import com.le.log.annotation.SystemLog;
import com.le.web.util.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WXY
 * @since 2019-05-14
 */
@Slf4j
@RestController
@RequestMapping("/admin/cs/chatRecord")
public class ChatRecordController {
    @Autowired
    private IChatRecordService chatRecordService;

    /**
     * 获取分页数据
     *
     * @param search 搜索条件
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:chatRecord:view')")
    @SystemLog("查看列表")
    public R page(ChatRecord search) {
        Page<ChatRecord> page = HttpContextUtils.getPage();
        return chatRecordService.findPage(page, search);
    }

    /**
     * 添加或者更新
     *
     * @param chatRecord
     * @return
     */
    @RequestMapping("/editData")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:chatRecord:edit')")
    @SystemLog("编辑信息")
    public R editData(@Valid ChatRecord chatRecord) {
        return chatRecordService.editData(chatRecord);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:chatRecord:edit')")
    @SystemLog("删除")
    public R del(@RequestParam("ids") List<Long> ids){
        chatRecordService.removeByIds(ids);
        return R.success();
    }
}
