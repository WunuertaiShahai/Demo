package com.example.Demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 注意使用@Controller，它用于返回视图名称
public class HelloController {

    @GetMapping("/hello") // 当访问 /hello 时，调用此方法
    public String sayHello(Model model) {
        // 向模板中添加动态数据
//        model.addAttribute("message", "欢迎使用 Thymeleaf！");
        model.addAttribute("serverTime", java.time.LocalDateTime.now());
        // 返回模板的逻辑视图名，对应 templates/hello.html
        return "home";
    }
}