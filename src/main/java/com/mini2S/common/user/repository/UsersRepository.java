package com.mini2S.common.user.repository;

import com.mini2S.common.user.model.dto.UsersCoordDto;
import com.mini2S.common.user.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserEmail(String email);

    /**
     * 사용자 email 로 사용자 조회
     *
     * @param userEmail 사용자 이메일
     * @return Users
     */
    Users findByUserEmailOrderByUserSeq(String userEmail);

    /**
     * 사용자 위치 조회
     *
     * @param userEmail 사용자 이메일
     * @return UsersCoordDto
     */
    @Query("SELECT new com.mini2S.common.user.model.dto.UsersCoordDto ( " +
            " a.coordX, a.coordY )" +
            " FROM Users a " +
            " WHERE a.userEmail = :userEmail")
    UsersCoordDto findUserCoordByUserEmail(@Param("userEmail") String userEmail);
}
