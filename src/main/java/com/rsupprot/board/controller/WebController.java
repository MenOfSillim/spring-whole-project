package com.rsupprot.board.controller;

import com.rsupprot.board.service.PostsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class WebController {

    private final PostsService postsService;

    // handlebars-spring-boot-starter 덕분에 컨트롤러에서 문자열을 반환할때 앞의 path와 뒤의 파일 확장자는 자동으로 지정됩니다.
    // (prefix: src/main/resources/templates, suffix: .hbs)
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("posts", postsService.findAllDesc(0, 10));
        return "main";
    }
    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }
}