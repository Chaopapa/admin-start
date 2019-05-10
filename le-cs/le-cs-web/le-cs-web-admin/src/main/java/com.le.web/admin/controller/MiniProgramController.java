package com.le.web.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.MiniProgram;
import com.le.cs.service.IMiniProgramService;
import com.le.core.rest.R;
import com.le.log.annotation.SystemLog;
import com.le.system.entity.SysRole;
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
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WXY
 * @since 2019-05-10
 */
@Slf4j
@RestController
@RequestMapping("/admin/cs/miniProgram")
public class MiniProgramController {
    @Autowired
    private IMiniProgramService miniProgramService;
    /**
     * 获取分页数据
     *
     * @param search 搜索条件
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:miniProgram:view')")
    @SystemLog("查看列表")
    public R page(MiniProgram search) {
        Page<MiniProgram> page = HttpContextUtils.getPage();
        return miniProgramService.findPage(page, search);
    }

    /**
     * 跳转角色信息页
     */
    @RequestMapping("/detail")
    @PreAuthorize("hasPermission(null,'cs:miniProgram:view')")
    @SystemLog("查看详情")
    public R detail(Long id) {
        MiniProgram miniProgram = miniProgramService.getById(id);
        return R.success().putData("miniProgram", miniProgram);
    }

    /**
     * 添加或者更新
     *
     * @param miniProgram
     * @return
     */
    @RequestMapping("/editData")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:miniProgram:edit')")
    @SystemLog("编辑信息")
    public R editData(@Valid MiniProgram miniProgram) {
        return miniProgramService.editData(miniProgram);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:miniProgram:edit')")
    @SystemLog("删除")
    public R del(@RequestParam("ids") List<Long> ids){
        miniProgramService.removeByIds(ids);
        return R.success();
    }
}
