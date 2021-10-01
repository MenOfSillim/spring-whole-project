package com.mini2S.common.role.service;

import com.mini2S.common.role.model.entity.Roles;
import com.mini2S.common.role.repository.RolesRepository;
import com.mini2S.model.response.CommonResult;
import com.mini2S.service.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class RolesServiceImpl implements RolesService {
    private final RolesRepository rolesRepository;
    private final ResponseService responseService;

    /**
     * 권한 추가
     * @param roleName 권한 이름
     * @return Roles
     */
    @Override
    @Transactional
    public CommonResult createNewRole(String roleName) {
        Roles role = rolesRepository.findRolesByRoleName(roleName); // 해당 권한 이름이 있는지 조회
        List<String> roleNameList = rolesRepository.findAllRoleName(); // 권한 이름 전부 조회
        if (role == null || !roleNameList.contains(roleName)) {
            rolesRepository.save(Roles.builder()
                            .roleName(roleName)
                            .build()); // 새로운 권한 생성
            return responseService.getSuccessResult();
        }
        return responseService.getFailResult();
    }
}
