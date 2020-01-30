package com.le.web.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.cs.entity.Customer;
import com.le.cs.service.ICustomerService;
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
@RequestMapping("/admin/cs/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    /**
     * 获取分页数据
     *
     * @param search 搜索条件
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:customer:view')")
    @SystemLog("查看列表")
    public R page(Customer search) {
        Page<Customer> page = HttpContextUtils.getPage();
        return customerService.findPage(page, search);
    }

    /**
     * 跳转角色信息页
     */
    @RequestMapping("/detail")
    @PreAuthorize("hasPermission(null,'cs:customer:view')")
    @SystemLog("查看详情")
    public R detail(Long id) {
        Customer customer = customerService.getById(id);
        return R.success().putData("customer", customer);
    }

    /**
     * 添加或者更新
     *
     * @param customer
     * @return
     */
    @RequestMapping("/editData")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:customer:edit')")
    @SystemLog("编辑信息")
    public R editData(@Valid Customer customer) {
        return customerService.editData(customer);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    @PreAuthorize("hasPermission(null ,'cs:customer:edit')")
    @SystemLog("删除")
    public R del(@RequestParam("ids") List<Long> ids){
        customerService.removeByIds(ids);
        return R.success();
    }
}
