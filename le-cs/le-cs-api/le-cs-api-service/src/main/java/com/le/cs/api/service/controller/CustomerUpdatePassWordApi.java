package com.le.cs.api.service.controller;

import com.le.core.rest.R;
import com.le.cs.service.ICustomerServiceService;
import com.le.cs.vo.CustomerLoginVo;
import com.le.cs.vo.PassWordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/service/customer")
public class CustomerUpdatePassWordApi {

    @Autowired
    private ICustomerServiceService customerServiceService;

    /**
     * 手机验证修改用户密码
     *
     * @param customerLoginVo
     * @param passWordVo
     * @return
     */
    @RequestMapping("updatePasswordValidate")
    public R updatePasswordValidate(@Valid CustomerLoginVo customerLoginVo, @Valid PassWordVo passWordVo) {
        return customerServiceService.updatePasswordValidate(customerLoginVo, passWordVo);
    }
}
