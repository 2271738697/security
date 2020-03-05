package com.example.security.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoudb
 * @date 2019/12/23 16:59
 */
@RestController
public class HelloController {
    @GetMapping("admin/hello")
    public String hello() {
        return "adminHello";
    }
    @GetMapping("user/hello")
    public String hello02() {
        return "userHello";
    }

}
