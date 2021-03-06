package com.mini2S.common.controller;

import com.mini2S.biz.contract.model.dto.InsertContractDto;
import com.mini2S.common.util.qrcode.QrCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@Slf4j
@AllArgsConstructor
public class WebController {

    @GetMapping("/login")
    public String login(InsertContractDto dto, Model model, HttpServletRequest request){

        // 1. 다른 파일들
//        final DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
//        Resource resource = defaultResourceLoader.getResource("classpath:static/image/오목교점_메인.png");
//        String path = resource.getFile().getAbsolutePath();
//        System.out.println(path);
//
//        String path1 = QrCode.createQRCodeImage("contract", "1", "qr코드1", "https://naver.com");
//        System.out.println(path1);
        return "/login";
    }
}
