package com.mini2S.biz.unit.repository;

import com.mini2S.biz.unit.model.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    @Query(value = " SELECT a.* " +
            " FROM unit a " +
            " WHERE a.branch_seq = :branchSeq "
            , nativeQuery = true)
    List<Unit> findByBranchSeq(@Param("branchSeq") Long branchSeq);
}
