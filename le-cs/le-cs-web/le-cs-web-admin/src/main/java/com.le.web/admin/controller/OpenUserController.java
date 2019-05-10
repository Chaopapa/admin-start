package com.le.cs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.core.annotation.SystemLog;
import com.le.cs.entity.OpenUser;
import com.le.cs.service.IOpenUserService;
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
 * @since 2019-05-10
 */
@Slf4j
@Controller
@RequestMapping("/admin/cs/open-user")
public class OpenUserController {
    @Autowired
    private IOpenUserService openUserService;

    /**
     * 跳转首页
     *
     * @param model
     * @return
     */
    @RequestMapping({"/index", "/"})
    @RequiresPermissions("cs:openUser:view")
    public String index(ModelMap model) {
        return "admin/cs/openUser/index";
    }

    /**
     * 获取分页数据
     *
     * @param search 搜索条件
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    @RequiresPermissions("cs:openUser:view")
    @SystemLog("查看列表")
    public R page(OpenUser search) {
        Page<OpenUser> page = HttpContextUtils.getPage();
        return openUserService.findPage(page, search);
    }

    /**
     * 跳转信息页
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    @RequiresPermissions("cs:openUser:view")
    @SystemLog("查看详情")
    public String edit(ModelMap model, Long id) {
        if (id != null) {
            OpenUser openUser = openUserService.getById(id);
            model.put("entity", openUser);
        }
        return "admin/cs/openUser/edit";
    }

    /**
     * 添加或者更新
     *
     * @param openUser
     * @return
     */
    @RequestMapping("/editData")
    @ResponseBody
    @RequiresPermissions("cs:openUser:edit")
    @SystemLog("编辑信息")
    public R editData(@Valid OpenUser openUser) {
        return openUserService.editData(openUser);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    @RequiresPermissions("cs:openUser:edit")
    @SystemLog("删除")
    public R del(@RequestParam("ids") List<Long> ids){
        openUserService.removeByIds(ids);
        return R.success();
    }
}
