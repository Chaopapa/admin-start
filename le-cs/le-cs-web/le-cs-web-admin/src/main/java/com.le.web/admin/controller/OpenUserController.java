package com.le.web.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.CustomerService;
import com.le.cs.entity.OpenUser;
import com.le.cs.service.IOpenUserService;
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
 * @since 2019-05-10
 */
@Slf4j
@RestController
@RequestMapping("/admin/cs/openUser")
public class OpenUserController {
    @Autowired
    private IOpenUserService openUserService;

    /**
     * 获取分页数据
     *
     * @param search 搜索条件
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:openUser:view')")
    @SystemLog("查看列表")
    public R page(OpenUser search) {
        Page<OpenUser> page = HttpContextUtils.getPage();
        return openUserService.findPage(page, search);
    }

    /**
     * 添加或者更新
     *
     * @param openUser
     * @return
     */
    @RequestMapping("/editData")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:openUser:edit')")
    @SystemLog("编辑信息")
    public R editData(@Valid OpenUser openUser) {
        return openUserService.editData(openUser);
    }

    /**
     * 跳转角色信息页
     */
    @RequestMapping("/detail")
    @PreAuthorize("hasPermission(null,'cs:openUser:view')")
    @SystemLog("查看详情")
    public R detail(Long id) {
        OpenUser openUser = openUserService.getById(id);
        return R.success().putData("openUser", openUser);
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:openUser:edit')")
    @SystemLog("删除")
    public R del(@RequestParam("ids") List<Long> ids){
        openUserService.removeByIds(ids);
        return R.success();
    }

    /**
     * 检查用户名
     *
     * @param id       用户id
     * @param username 登录名
     * @return
     */
    @RequestMapping(value = "/checkUserName")
    @ResponseBody
    @PreAuthorize("hasPermission(null,'cs:openUser:edit')")
    public R checkUserName(@RequestParam(required = false) Long id, String username) {
        boolean exist = openUserService.usernameExists(id, username);
        return R.success().putData("exist", exist);
    }
}
