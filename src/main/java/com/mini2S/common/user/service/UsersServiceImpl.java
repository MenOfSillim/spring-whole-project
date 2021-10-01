package com.mini2S.common.user.service;

import com.mini2S.common.enums.RoleEnum;
import com.mini2S.configuration.security.JwtTokenProvider;
import com.mini2S.configuration.security.TokenDto;
import com.mini2S.configuration.security.TokenRepository;
import com.mini2S.configuration.security.RefreshToken;
import com.mini2S.model.dto.TokenRequestDto;
import com.mini2S.common.user.model.dto.UsersSigninDto;
import com.mini2S.common.role.model.entity.Roles;
import com.mini2S.common.user.model.entity.Users;
import com.mini2S.common.user.model.dto.UsersSignupDto;
import com.mini2S.common.role.repository.RolesRepository;
import com.mini2S.common.user.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {
    private final Long ROLESEQ = 1L;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;

    /**
     * 로그인시 jwt 토큰 발급
     *
     * @param dto 로그인을 위한 dto
     * @return TokenDto
     */
    @Override
    @Transactional
    public TokenDto signIn(UsersSigninDto dto) {
        Users users = usersRepository.findByUserEmailOrderByUserSeq(dto.getUserEmail());
        // 유저 있음
        if (users != null && users.getUserEmail().equals(dto.getUserEmail())) {
            if (passwordEncoder.matches(dto.getUserPw(), users.getUserPw())) {
                //로그인 성공
                String roleName = rolesRepository.findRoleNameByUserSeq(users.getUserSeq());
                String refreshToken = jwtTokenProvider.createRefreshtoken(UUID.randomUUID().toString());
                tokenRepository.save(RefreshToken
                        .builder()
                        .userEmail(dto.getUserEmail())
                        .refreshToken(refreshToken)
                        .build());
                // 리프레시 토큰 삽입
                return TokenDto.builder()
                        .accessToken(jwtTokenProvider.createAccessToken(users.getUserEmail(), roleName))
                        .refreshToken(refreshToken)
                        .build();
            } else {
                //패스워드 틀림
                return null;
            }
            // 유저 없음
        } else {
            return null;
        }
    }

    /**
     * 회원가입
     *
     * @param dto 회원가입을 위한 dto
     * @return Users
     */
    @Override
    @Transactional
    public Users signUpUser(UsersSignupDto dto) {
        usersRepository.findByUserEmailOrderByUserSeq(dto.getUserEmail());
        String encodePassword = passwordEncoder.encode(dto.getUserPw());
        Roles roles = rolesRepository.findByRoleSeq(ROLESEQ); // 회원가입시 최초 권한 조회
        if (roles == null || !roles.getRoleName().equals(RoleEnum.ROLENAME.getRoleName())) {
            rolesRepository.save(Roles.builder()
                    .roleName(RoleEnum.ROLENAME.getRoleName())
                    .build()); // 최초 권한 없으면 새로 만들어줌
            roles = rolesRepository.findByRoleSeq(ROLESEQ);
        }
        Users returnUser = usersRepository.save(Users.builder()
                .userEmail(dto.getUserEmail())
                .userName(dto.getUserName())
                .userAccountType(dto.getUserAccountType())
                .userPw(encodePassword)
                .userPhoneNumber(dto.getUserPhoneNumber())
                .userAddress(dto.getAddress())
                .userDetailAddress(dto.getAddressDetail())
                .build()); // 회원가입
        Users user = usersRepository.findByUserEmailOrderByUserSeq(dto.getUserEmail());
        rolesRepository.insertUserRole(user.getUserSeq(), roles.getRoleSeq()); // 가입한 사용자에게 권한 부여
        return returnUser;

    }

    /**
     * 토큰 재발급
     *
     * @param dto access 토큰
     * @return TokenDto
     */
    @Override
    @Transactional
    public TokenDto reIssue(TokenRequestDto dto) {
        // 1. refreshToken 인증
        if (!jwtTokenProvider.validateToken(dto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. accessToken에서 userEmail 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(dto.getAccessToken());

        // 3. 저장소에서 userEmail 기반으로 refreshToken 가져오기
        RefreshToken refreshToken = tokenRepository.findRefreshTokenByUserEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃된 사용자입니다."));

        // 4. refreshToken 일치 검사
        if (!refreshToken.getRefreshToken().equals(dto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }
        Users users = usersRepository.findByUserEmailOrderByUserSeq(authentication.getName());

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = TokenDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(
                        authentication.getName(),
                        rolesRepository.findRoleNameByUserSeq(users.getUserSeq())))
                .refreshToken(jwtTokenProvider.createRefreshtoken(UUID.randomUUID().toString())).build();

        // 6. 저장소 정보 업데이트
        tokenRepository.save(RefreshToken.builder()
                .userEmail(users.getUserEmail())
                .refreshToken(tokenDto.getRefreshToken())
                .build());
        return tokenDto;
    }
}
