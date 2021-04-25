package com.mini2S.restapi;

import com.mini2S.dto.UsersSigninDto;
import com.mini2S.dto.UsersSignupDto;
import com.mini2S.entity.Users;
import com.mini2S.model.response.CommonResult;
import com.mini2S.service.ResponseService;
import com.mini2S.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = {"1. Users"})
@RestController
@AllArgsConstructor
public class RestUsersController {
    private final UsersService usersService;
    // 결과 반환 Service
    private final ResponseService responseService;
    // 로그인
    @PostMapping("/signin")
    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인")
    public long signin(@RequestBody UsersSigninDto dto, HttpServletRequest request){
        long result = usersService.signin(dto);
        if(result == 1){
            HttpSession hs = request.getSession();
            hs.setAttribute("users", dto);
        }
        return result;
    }

    // 회원가입
    @PostMapping("/signup")
    @ApiOperation(value = "회원가입", notes = "가입 경로 포함시켜야됨")
    public CommonResult signup(@RequestBody UsersSignupDto dto){
        usersService.signUpUser(Users.builder()
                        .userEmail(dto.getUserEmail())
                        .userName(dto.getUserName())
                        .userAccountType(dto.getUserAccountType())
                        .userPw(dto.getUserPw())
                        .userGender(dto.getUserGender())
                        .userPhoneNumber(dto.getUserPhoneNumber())
                        .build());
        return responseService.getSuccessResult();
    }


}