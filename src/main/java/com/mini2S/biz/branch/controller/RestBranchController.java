package com.mini2S.biz.branch.controller;

import com.mini2S.biz.branch.model.dto.BranchDto;
import com.mini2S.biz.branch.service.BranchServiceImpl;
import com.mini2S.common.exception.CommonException;
import com.mini2S.model.response.ListResult;
import com.mini2S.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"3. Branch"})
@RequestMapping("/v1")
@RestController
@AllArgsConstructor
public class RestBranchController {

    private final BranchServiceImpl branchService;
    private final ResponseService responseService;

    /**
     * @return 사용자 기반으로 한 지점 목록 거리순으로 정렬하여 반환
     */
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true,
                    dataType = "String",
                    paramType = "header"
            )
    })
    @PostMapping("/branch/list/signin")
    @ApiOperation(value = "지점 목록(로그인)")
    public ListResult<BranchDto> signInBranchList() throws CommonException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            System.out.println("이메일 : " + userEmail);
            return responseService.getListResult(branchService.selectUserBranchList(userEmail));
        } catch (Exception e) {
            throw new CommonException("signInBranchList", e);
        }
    }

    /**
     * @return 기본 값을 기반으로 지점 목록 거리순으로 정렬하여 반환
     */
    @PostMapping("/branch/list/nonsignin")
    @ApiOperation(value = "지점 목록(비로그인)")
    public ListResult<BranchDto> signOutBranchList() {
        return responseService.getListResult(branchService.selectBranchInfoList());
    }
}
