DROP TABLE IF EXISTS WISH;
DROP TABLE IF EXISTS TRANSACTION;
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS FOLLOW;
DROP TABLE IF EXISTS MAKER;
DROP TABLE if exists ROLE;
DROP TABLE IF EXISTS USER;


-- 사용자
CREATE TABLE `USER`
(
    `user_id`       BIGINT       NOT NULL AUTO_INCREMENT primary key COMMENT '사용자 번호', -- 사용자 번호
    `login_id`      VARCHAR(255) NOT NULL COMMENT '로그인 아이디',                           -- 로그인 아이디
    `password`      VARCHAR(255) NOT NULL COMMENT '패스워드',                              -- 패스워드
    `created_date`  DATETIME     NOT NULL COMMENT '가입 날짜',                             -- 가입 날짜
    `modified_date` DATETIME     NULL COMMENT '수정 날짜',                                 -- 수정 날짜
    `removed_date`  DATETIME     NULL COMMENT '탈퇴 날짜'                                  -- 탈퇴 날짜
)
    COMMENT '사용자';


-- 사용자 권한
CREATE TABLE `ROLE`
(
    `role`    VARCHAR(50) NULL COMMENT '사용자 권한', -- 사용자 권한
    `user_id` BIGINT      NULL COMMENT '사용자 번호'  -- 사용자 번호
)
    COMMENT '사용자 권한';

-- 사용자 권한
ALTER TABLE `ROLE`
    ADD CONSTRAINT `FK_USER_TO_ROLE` -- 사용자 -> 사용자 권한
        FOREIGN KEY (
                     `user_id` -- 사용자 번호
            )
            REFERENCES `USER` ( -- 사용자
                               `user_id` -- 사용자 번호
                );

-- 펀딩 메이커
CREATE TABLE `MAKER`
(
    `maker_id`                     BIGINT       NOT NULL AUTO_INCREMENT primary key COMMENT '메이커 번호', -- 메이커 번호
    `company_name`                 VARCHAR(255) NULL COMMENT '회사명',                                   -- 회사명
    `business_registration_number` VARCHAR(255) NULL COMMENT '사업자등록번호',                               -- 사업자등록번호
    `phone`                        VARCHAR(20)  NULL COMMENT '전화번호',                                  -- 전화번호
    `user_id`                      BIGINT       NOT NULL COMMENT '사용자 번호'                             -- 사용자 번호
)
    COMMENT '펀딩 메이커';

-- 제품
CREATE TABLE `PRODUCT`
(
    `product_id`    BIGINT       NOT NULL AUTO_INCREMENT primary key COMMENT '제품 번호', -- 제품 번호
    `company_name`  VARCHAR(255) NULL COMMENT '회사명',                                  -- 회사명
    `product_name`  VARCHAR(255) NOT NULL COMMENT '제품명',                              -- 제품명
    `contents`      VARCHAR(255) NOT NULL COMMENT '제품 설명',                            -- 제품 설명
    `price`         BIGINT       NOT NULL COMMENT '제품 가격',                            -- 제품 가격
    `success_price` BIGINT       NOT NULL COMMENT '펀딩 성공 금액',                         -- 펀딩 성공 금액
    `max_quantity`  BIGINT       NOT NULL COMMENT '최대 판매 수량',                         -- 최대 판매 수량
    `success_rate`  DOUBLE      NULL COMMENT '펀딩 성공률',                               -- 펀딩 성공률
    `start_date`    DATETIME     NOT NULL COMMENT '펀딩 시작일',                           -- 펀딩 시작일
    `end_date`      DATETIME     NOT NULL COMMENT '펀딩 종료일',                           -- 펀딩 종료일
    `maker_id`      BIGINT       NOT NULL COMMENT '메이커 번호'                            -- 메이커 번호
)
    COMMENT '제품';

-- 거래 내용
CREATE TABLE `TRANSACTION`
(
    `transaction_id`     BIGINT      NOT NULL AUTO_INCREMENT primary key COMMENT '거래 아이디', -- 거래 아이디
    `account_number`     VARCHAR(20) NOT NULL COMMENT '계좌 번호',                             -- 계좌 번호
    `account_password`   VARCHAR(20) NOT NULL COMMENT '계좌 비밀번호',                           -- 계좌 비밀번호
    `is_paid`            BOOLEAN     NULL COMMENT '거래 상태',                                 -- 거래 상태
    `is_participating`   BOOLEAN     NULL COMMENT '펀딩 참여 상태',                              -- 펀딩 참여 상태
    `user_id`            BIGINT      NULL COMMENT '사용자 번호',                                -- 사용자 번호
    `product_id`         BIGINT      NULL COMMENT '제품 번호'                                  -- 제품 번호
)
    COMMENT '거래 내용';

-- 팔로우
CREATE TABLE `FOLLOW`
(
    `follow_id` BIGINT NOT NULL AUTO_INCREMENT primary key COMMENT '팔로우 번호', -- 팔로우 번호
    `user_id`   BIGINT NOT NULL COMMENT '사용자 번호',                            -- 사용자 번호
    `maker_id`  BIGINT NOT NULL COMMENT '메이커 번호'                             -- 메이커 번호
)
    COMMENT '팔로우';

-- 찜하기
CREATE TABLE `WISH`
(
    `wish_id`    BIGINT NOT NULL AUTO_INCREMENT primary key COMMENT '찜하기 번호', -- 찜하기 번호
    `user_id`    BIGINT NOT NULL COMMENT '사용자 번호',                            -- 사용자 번호
    `product_id` BIGINT NOT NULL COMMENT '제품 번호'                              -- 제품 번호
)
    COMMENT '찜하기';

-- 펀딩 메이커
ALTER TABLE `MAKER`
    ADD CONSTRAINT `FK_USER_TO_MAKER` -- 사용자 -> 펀딩 메이커
        FOREIGN KEY (
                     `user_id` -- 사용자 번호
            )
            REFERENCES `USER` ( -- 사용자
                               `user_id` -- 사용자 번호
                );

-- 제품
ALTER TABLE `PRODUCT`
    ADD CONSTRAINT `FK_MAKER_TO_PRODUCT` -- 펀딩 메이커 -> 제품
        FOREIGN KEY (
                     `maker_id` -- 메이커 번호
            )
            REFERENCES `MAKER` ( -- 펀딩 메이커
                                `maker_id` -- 메이커 번호
                );

-- 팔로우
ALTER TABLE `FOLLOW`
    ADD CONSTRAINT `FK_USER_TO_FOLLOW` -- 사용자 -> 팔로우
        FOREIGN KEY (
                     `user_id` -- 사용자 번호
            )
            REFERENCES `USER` ( -- 사용자
                               `user_id` -- 사용자 번호
                );

-- 팔로우
ALTER TABLE `FOLLOW`
    ADD CONSTRAINT `FK_MAKER_TO_FOLLOW` -- 펀딩 메이커 -> 팔로우
        FOREIGN KEY (
                     `maker_id` -- 메이커 번호
            )
            REFERENCES `MAKER` ( -- 펀딩 메이커
                                `maker_id` -- 메이커 번호
                );

-- 찜하기
ALTER TABLE `WISH`
    ADD CONSTRAINT `FK_USER_TO_WISH` -- 사용자 -> 찜하기
        FOREIGN KEY (
                     `user_id` -- 사용자 번호
            )
            REFERENCES `USER` ( -- 사용자
                               `user_id` -- 사용자 번호
                );

-- 찜하기
ALTER TABLE `WISH`
    ADD CONSTRAINT `FK_PRODUCT_TO_WISH` -- 제품 -> 찜하기
        FOREIGN KEY (
                     `product_id` -- 제품 번호
            )
            REFERENCES `PRODUCT` ( -- 제품
                                  `product_id` -- 제품 번호
                );

-- 거래 내용
ALTER TABLE `TRANSACTION`
    ADD CONSTRAINT `FK_USER_TO_TRANSACTION` -- 사용자 -> 거래 내용
        FOREIGN KEY (
                     `user_id` -- 사용자 번호
            )
            REFERENCES `USER` ( -- 사용자
                               `user_id` -- 사용자 번호
                );

-- 거래 내용
ALTER TABLE `TRANSACTION`
    ADD CONSTRAINT `FK_PRODUCT_TO_TRANSACTION` -- 제품 -> 거래 내용
        FOREIGN KEY (
                     `product_id` -- 제품 번호
            )
            REFERENCES `PRODUCT` ( -- 제품
                                  `product_id` -- 제품 번호
                );