package com.le.admin.component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.component.sms.entity.ComSmslog;
import com.le.component.sms.entity.enums.SmsType;
import com.le.component.sms.service.IComSmslogService;
import com.le.core.rest.R;
import com.le.web.util.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 短信记录 前端控制器
 * </p>
 *
 * @author 严秋旺
 * @since 2018-11-19
 */
@Slf4j
@Controller
@RequestMapping("/admin/component/smslog")
public class SmslogController {
    @Autowired
    private IComSmslogService comSmslogService;

    /**
     * : 跳转短信记录首页
     */
    @RequestMapping({"/index", "/"})
//    @RequiresPermissions("component:smslog:view")
    public String index(ModelMap model) {
        model.addAttribute("smsTypes", SmsType.values());
        return "admin/component/smslog/index";
    }

    /**
     * 获取短信记录分页数据
     */
    @RequestMapping("/page")
    @ResponseBody
//    @RequiresPermissions("component:smslog:view")
    public R page(ComSmslog search) {
        Page<ComSmslog> page = HttpContextUtils.getPage();
        return comSmslogService.findPage(page, search);
    }

}
