package com.mini2S.biz.branch.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mini2S.biz.branch.model.dto.BranchDto;
import com.mini2S.biz.branch.service.BranchServiceImpl;
import com.mini2S.common.exception.CommonException;
import com.mini2S.model.response.ListResult;
import com.mini2S.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

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
    @PostMapping("/branch/list/signin")
    @ApiOperation(value = "지점 목록(로그인)")
    @ApiImplicitParam(
            name = "X-AUTH-TOKEN",
            value = "로그인 성공 후 AccessToken",
            required = true,
            dataType = "String",
            paramType = "header"
    )
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

    /**
     * return 타입을 JsonArray 로 하였을 때
     * 에러 발생
     *
     * @return JsonArray
     */
//    @PostMapping("/branch/list/nonsignin")
//    @ApiOperation(value = "스웨거 테스트 Json")
//    public JsonArray jsonReturn() {
//        JsonArray jsonArray = new JsonArray();
//        List<BranchDto> listResult = branchService.selectBranchInfoList();
//        listResult.forEach(item -> {
//            JsonObject returnJson = new JsonObject();
//            returnJson.addProperty("address", item.getAddress());
//            returnJson.addProperty("addressDetail", item.getAddressDetail());
////            returnJson.addProperty("branchImange", item.getBranchImage());
//            returnJson.addProperty("branchName", item.getBranchName());
//            returnJson.addProperty("coordX", item.getCoordX());
//            returnJson.addProperty("coordY", item.getCoordY());
//            jsonArray.add(returnJson);
//        });
//
//        return jsonArray;
//    }

    /**
     * return 타입을 JsonObject 로 하였을 때
     * 에러 발생
     *
     * @return
     */
//    @PostMapping("/branch/list/nonsignin")
//    @ApiOperation(value = "스웨거 테스트 Json")
//    public JsonObject jsonReturn() {
//        List<BranchDto> listResult = branchService.selectBranchInfoList();
//        JsonObject returnJson = new JsonObject();
//        returnJson.addProperty("address", listResult.get(2).getAddress());
//        returnJson.addProperty("addressDetail", listResult.get(2).getAddressDetail());
////            returnJson.addProperty("branchImange", listResult.get(2).getBranchImage());
//        returnJson.addProperty("branchName", listResult.get(2).getBranchName());
//        returnJson.addProperty("coordX", listResult.get(2).getCoordX());
//        returnJson.addProperty("coordY", listResult.get(2).getCoordY());
//
//        return returnJson;
//    }

    /**
     * return 타입을 map 으로 하였을 때
     * 결과는 list 와 다를것 없음
     *
     * @return Map
     */
//    @PostMapping("branch/list/nonsignin")
//    @ApiOperation(value = "스웨거 테스트 map")
//    public Map<String, Object> mapReturn() {
//        List<BranchDto> listResult = branchService.selectBranchInfoList();
//        Map<String, Object> map = new HashMap<>();
//        map.put("address", listResult.get(2).getAddress());
//        map.put("addressDetail", listResult.get(2).getAddressDetail());
//        map.put("branchImage", listResult.get(2).getBranchImage());
//        map.put("branchName", listResult.get(2).getBranchName());
//        map.put("coordX", listResult.get(2).getCoordX());
//        map.put("coordY", listResult.get(2).getCoordY());
//        map.put("diffDistance", listResult.get(2).getDiffDistance());
//        return map;
//    }
}
