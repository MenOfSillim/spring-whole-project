package com.mini2S.biz.branch.service;

import com.mini2S.biz.branch.model.dto.BranchDto;
import com.mini2S.biz.branch.model.dto.BranchListDto;
import com.mini2S.common.enums.BranchEnum;
import com.mini2S.common.user.model.dto.UsersCoordDto;
import com.mini2S.common.util.DataHandleUtil;
import com.mini2S.common.util.map.Direction5;
import com.mini2S.common.user.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final UsersRepository usersRepository;

    /**
     * 로그인 시 지점 목록 출력
     *
     * @param userEmail 로그인 사용자 이메일
     * @return List<BranchDto>
     */
    @Override
    @Transactional
    public List<BranchDto> selectUserBranchList(String userEmail) {
        UsersCoordDto userCoordDto = usersRepository.findUserCoordByUserEmail(userEmail); // 사용자의 위치를 가져온다
        List<BranchListDto> branchListDtoList = branchRepository.findBranchsInfoByUseYn(); // 현재 useYn = Y 인 지점을 가져온다
        List<BranchDto> returnList = new ArrayList<>(); // 반환할 list 생성
        branchListDtoList.forEach(list -> {
            List<String> branchList = branchRepository.findBranchImageList(list.getBranchSeq()); // 사용 중인 지점의 이미지를 가져온다
            for (int i = 0; i < branchList.size(); i++) {
                branchList.set(i, "/image/branch/" + list.getBranchName() + "/" + branchList.get(i)); // 이미지 경로를 바꿔준다
            }
            returnList.add(BranchDto.builder()
                    .coordX(list.getCoordX())
                    .coordY(list.getCoordY())
                    .diffDistance(Direction5.selectDistanceAFromB(
                            Direction5.selectNavigationInfo(userCoordDto.getCoordX()
                                    , userCoordDto.getCoordY()
                                    , list.getCoordX()
                                    , list.getCoordY())))
                    .branchName(list.getBranchName())
                    .address(list.getAddress())
                    .addressDetail(list.getAddressDetail())
                    .branchImage(branchList)
                    .build());
        });
        returnList.sort(Comparator.comparingDouble(o -> DataHandleUtil.objectToDouble(o.getDiffDistance().get("distance")))); // 거리순으로 정렬한다
        return returnList;
    }

    /**
     * 비로그인 시 지점 목록 출력
     *
     * @return List<BranchDto>
     */
    @Override
    @Transactional
    public List<BranchDto> selectBranchInfoList() {
        List<BranchListDto> branchListDtoList = branchRepository.findBranchsInfoByUseYn(); // 현재 useYn = Y 인 지점을 가져온다
        List<BranchDto> returnList = new ArrayList<>();
        branchListDtoList.forEach(list -> {
            List<String> branchList = branchRepository.findBranchImageList(list.getBranchSeq()); // 사용 중인 지점의 이미지를 가져온다
            for (int i = 0; i < branchList.size(); i++) {
                branchList.set(i, "/image/branch/" + list.getBranchName() + "/" + branchList.get(i)); // 이미지 경로를 바꿔준다
            }
            returnList.add(BranchDto.builder()
                    .coordX(list.getCoordX())
                    .coordY(list.getCoordY())
                    .diffDistance(Direction5.selectDistanceAFromB(
                            Direction5.selectNavigationInfo(BranchEnum.DEFAULT_X.getValue()
                                    , BranchEnum.DEFAULT_Y.getValue()
                                    , list.getCoordX()
                                    , list.getCoordY())))
                    .branchName(list.getBranchName())
                    .address(list.getAddress())
                    .addressDetail(list.getAddressDetail())
                    .branchImage(branchList)
                    .build());
        });
        returnList.sort(Comparator.comparingDouble(o -> DataHandleUtil.objectToDouble(o.getDiffDistance().get("distance")))); // 거리순으로 정렬한다
        return returnList;
    }
}