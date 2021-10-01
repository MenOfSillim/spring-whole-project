package com.mini2S.biz.contract.controller;

import com.mini2S.biz.contract.model.dto.InsertContractDto;
import com.mini2S.biz.contract.model.dto.SelectContractDto;
import com.mini2S.biz.contract.model.entity.Contract;
import com.mini2S.biz.contract.service.ContractService;
import com.mini2S.model.response.CommonResult;
import com.mini2S.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"5. Contract"})
@RequestMapping("/v1")
@RestController
@AllArgsConstructor
@Slf4j
public class RestContractController {

    private final ContractService contractService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true,
                    dataType = "String",
                    paramType = "header"
            )
    })
    @PostMapping("/contract/insert")
    @ApiOperation(value = "계약서 생성")
    public CommonResult insertContract(@RequestBody InsertContractDto dto, HttpServletRequest request) throws IOException {
        log.info("feature path : [{}]", request.getRequestURI().split("/")[2]);
        log.info("InsertContractDto : [{}]", dto);
        Contract result = contractService.insertContract(dto ,request.getRequestURI().split("/")[2]);
        return responseService.getSingleResult(InsertContractDto.of(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true,
                    dataType = "String",
                    paramType = "header"
            )
    })
    @GetMapping("/contract/select")
    @ApiOperation(value = "유저가 계약한 계약서 리스트 출력")
    public CommonResult selectContractList() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<SelectContractDto> selectList = contractService.selectContractList(authentication.getName())
                                            .stream()
                                            .map(SelectContractDto::of)
                                            .collect(Collectors.toList());
        return responseService.getListResult(selectList);
    }

    /*
  {"branchSeq": 1,
  "contractStatus": "1",
  "priceDeposit": 100000,
  "priceDiscountUnit": 3000,
  "priceTotal": 30000,
  "priceUnit": 30000,
  "unitSeq": 1,
  "usageDay": "30",
  "usageMonth": "1",
  "useStartDttm": "2021-04-19",
  "useEndDttm": "2021-05-19",
  "userSeq": 1}

    * */
}
