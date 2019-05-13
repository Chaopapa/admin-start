package com.le.cs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.core.annotation.SystemLog;
import com.le.cs.entity.WechatMp;
import com.le.cs.service.IWechatMpService;
import com.le.core.rest.R;
import com.le.core.util.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller
@RequestMapping("/admin/cs/wechat-mp")
public class WechatMpController {
    @Autowired
    private IWechatMpService wechatMpService;

    /**
     * 跳转首页
     *
     * @param model
     * @return
     */
    @RequestMapping({"/index", "/"})
    @RequiresPermissions("cs:wechatMp:view")
    public String index(ModelMap model) {
        return "admin/cs/wechatMp/index";
    }

    /**
     * 获取分页数据
     *
     * @param search 搜索条件
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    @RequiresPermissions("cs:wechatMp:view")
    @SystemLog("查看列表")
    public R page(WechatMp search) {
        Page<WechatMp> page = HttpContextUtils.getPage();
        return wechatMpService.findPage(page, search);
    }

    /**
     * 跳转信息页
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    @RequiresPermissions("cs:wechatMp:view")
    @SystemLog("查看详情")
    public String edit(ModelMap model, Long id) {
        if (id != null) {
            WechatMp wechatMp = wechatMpService.getById(id);
            model.put("entity", wechatMp);
        }
        return "admin/cs/wechatMp/edit";
    }

    /**
     * 添加或者更新
     *
     * @param wechatMp
     * @return
     */
    @RequestMapping("/editData")
    @ResponseBody
    @RequiresPermissions("cs:wechatMp:edit")
    @SystemLog("编辑信息")
    public R editData(@Valid WechatMp wechatMp) {
        return wechatMpService.editData(wechatMp);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    @RequiresPermissions("cs:wechatMp:edit")
    @SystemLog("删除")
    public R del(@RequestParam("ids") List<Long> ids){
        wechatMpService.removeByIds(ids);
        return R.success();
    }
}
