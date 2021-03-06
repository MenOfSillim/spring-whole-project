package com.mini2S.biz.contract.model.entity;

import com.mini2S.biz.branch.model.entity.Branch;
import com.mini2S.biz.unit.model.entity.Unit;
import com.mini2S.common.user.model.entity.Users;
import com.mini2S.model.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Builder
@Entity
public class Contract extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTRACT_SEQ")
    private Long contractSeq;

    @OneToOne
    @JoinColumn(name = "USER_SEQ", nullable = false)
    private Users users;

    @OneToOne
    @JoinColumn(name = "BRANCH_SEQ", nullable = false)
    private Branch branch;

    @OneToOne
    @JoinColumn(name = "UNIT_SEQ", nullable = false)
    private Unit unit;

    @Column(columnDefinition = "varchar(5) not null comment '계약 상태'")
    private String contractStatus;

    @Column(columnDefinition = "varchar(250) comment '결제 시 발급되는 QR 이미지'  ")
    private String contractQrImage;

    @Column(columnDefinition = "bigint comment '구매할 유닛 금액'")
    private Long priceUnit;

    @Column(columnDefinition = "bigint comment '유닛 상품 할인 금액'")
    private Long priceDiscountUnit;

    @Column(columnDefinition = "bigint comment '물품 금액'")
    private Long priceGoods;

    @Column(columnDefinition = "bigint comment '물품 할인 금액'")
    private Long priceDiscountGoods;

    @Column(name = "price_etc", columnDefinition = "bigint comment '기타 금액'")
    private Long priceEtc;

    @Column(columnDefinition = "bigint comment '예치금, 보증금과 같은 개념'")
    private Long priceDeposit;

    @Column(columnDefinition = "bigint comment '총 결제 금액'")
    private Long priceTotal;

    @Column(columnDefinition = "bigint comment '미납 시 연체금'")
    private Long priceArrears;

    @Column(columnDefinition = "bigint comment '계약 종료 시 환불 및 추가 지급 금액'")
    private Long priceEnd;

    @Column(columnDefinition = "varchar(5) comment '사용개월, 정기계약 시 저장'")
    private String usageMonth;

    @Column(columnDefinition = "varchar(5) comment '사용일, 구독계약 시 저장'")
    private String usageDay;

    @Column(columnDefinition = "TIMESTAMP default '0000-00-00 00:00:00' comment '계약 확정 일자(예치금 결제 시)'"
            ,insertable = false, updatable = false)
    private LocalDateTime contractDttm;

    @Column(columnDefinition = "TIMESTAMP default '0000-00-00 00:00:00' comment '이용시작일자'")
    private LocalDateTime useStartDttm;

    @Column(columnDefinition = "TIMESTAMP default '0000-00-00 00:00:00' comment '이용종료일자'")
    private LocalDateTime useEndDttm;

    @Column(columnDefinition = "TIMESTAMP default '0000-00-00 00:00:00' comment '대금 미납 시 물품 처분일자'"
            ,insertable = false, updatable = false)
    private LocalDateTime disposalDttm;

}
