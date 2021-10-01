package com.mini2S.biz.contract.repository;

import com.mini2S.biz.contract.model.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findAllByUsersUserSeqOrderByCreatedDate(Long users_userSeq);
}
