package com.mini2S.common.role.repository;

import com.mini2S.common.role.model.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableJpaRepositories
public interface RolesRepository extends JpaRepository<Roles, Long> {

    /**
     * roleName 으로 권한 조회
     *
     * @param roleName 권한 이름
     * @return 해당 권한 정보
     */
    public Roles findRolesByRoleName(String roleName);

    /**
     * roleSeq 로 권한 조회
     *
     * @param roleSeq 권한 seq
     * @return 해당 권한 정보
     */
    public Roles findByRoleSeq(Long roleSeq);

    /**
     * 새로운 권한 추가
     *
     * @param UserSeq 사용자 seq
     * @param RoleSeq 권한 seq
     */
    @Transactional
    @Modifying
    @Query(value = " INSERT INTO user_role " +
            " ( " +
            "   user_seq, " +
            "   role_seq) " +
            " VALUES " +
            " (" +
            "   :UserSeq, " +
            "   :RoleSeq" +
            " ) "
            , nativeQuery = true)
    void insertUserRole(
            @Param("UserSeq") Long UserSeq,
            @Param("RoleSeq") Long RoleSeq
    );

    /**
     * 해당 사용자의 권한 조회
     *
     * @param userSeq 사용자 seq
     * @return 권한 이름
     */
    @Query(value = " SELECT a.role_name " +
            " FROM roles a " +
            " INNER JOIN user_role b " +
            " ON a.role_seq = b.role_seq " +
            " WHERE b.user_seq = :userSeq "
            , nativeQuery = true)
    public String findRoleNameByUserSeq(@Param("userSeq") Long userSeq);

    /**
     * 모든 권한 조회
     *
     * @return 권한 리스트
     */
    @Query(value = " SELECT r.roleName " +
            " FROM Roles r ")
    public List<String> findAllRoleName();

}
