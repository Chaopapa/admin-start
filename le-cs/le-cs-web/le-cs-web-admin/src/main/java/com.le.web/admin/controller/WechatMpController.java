package com.le.web.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.OpenUser;
import com.le.cs.entity.WechatMp;
import com.le.cs.service.IWechatMpService;
import com.le.core.rest.R;
import com.le.log.annotation.SystemLog;
import com.le.web.util.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
 * @since 2019-05-13
 */
@Slf4j
@RestController
@RequestMapping("/admin/cs/wechatMp")
public class WechatMpController {
    @Autowired
    private IWechatMpService wechatMpService;

    /**
     * 获取分页数据
     *
     * @param search 搜索条件
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:wechatMp:view')")
    @SystemLog("查看列表")
    public R page(WechatMp search) {
        Page<WechatMp> page = HttpContextUtils.getPage();
        return wechatMpService.findPage(page, search);
    }

    /**
     * 添加或者更新
     *
     * @param wechatMp
     * @return
     */
    @RequestMapping("/editData")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:wechatMp:edit')")
    @SystemLog("编辑信息")
    public R editData(@Valid WechatMp wechatMp) {
        return wechatMpService.editData(wechatMp);
    }

    /**
     * 跳转角色信息页
     */
    @RequestMapping("/detail")
    @PreAuthorize("hasPermission(null,'cs:wechatMp:view')")
    @SystemLog("查看详情")
    public R detail(Long id) {
        WechatMp wechatMp = wechatMpService.getById(id);
        return R.success().putData("wechatMp", wechatMp);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:wechatMp:edit')")
    @SystemLog("删除")
    public R del(@RequestParam("ids") List<Long> ids){
        wechatMpService.removeByIds(ids);
        return R.success();
    }
}
