DROP TABLE IF EXISTS WISH;
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS FOLLOW;
DROP TABLE IF EXISTS MAKER;
drop table if exists ROLE;
DROP TABLE IF EXISTS USER;

-- 사용자
CREATE TABLE `USER`
(
    `user_id`       BIGINT       NOT NULL COMMENT '사용자 번호',  -- 사용자 번호
    `login_id`      VARCHAR(255) NOT NULL COMMENT '로그인 아이디', -- 로그인 아이디
    `password`      VARCHAR(50)  NOT NULL COMMENT '패스워드',    -- 패스워드
    `created_date`  DATETIME     NOT NULL COMMENT '가입 날짜',   -- 가입 날짜
    `modified_date` DATETIME     NULL COMMENT '수정 날짜',       -- 수정 날짜
    `removed_date`  DATETIME     NULL COMMENT '탈퇴 날짜'        -- 탈퇴 날짜
)
    COMMENT '사용자';

-- 사용자
ALTER TABLE `USER`
    ADD CONSTRAINT `PK_USER` -- 사용자 기본키
        PRIMARY KEY (
                     `user_id` -- 사용자 번호
            );

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
    `maker_id`                     BIGINT       NOT NULL COMMENT '메이커 번호', -- 메이커 번호
    `business_registration_number` VARCHAR(255) NULL COMMENT '사업자등록번호',    -- 사업자등록번호
    `phone`                        VARCHAR(20)  NULL COMMENT '전화번호',       -- 전화번호
    `user_id`                      BIGINT       NOT NULL COMMENT '사용자 번호'  -- 사용자 번호
)
    COMMENT '펀딩 메이커';

-- 펀딩 메이커
ALTER TABLE `MAKER`
    ADD CONSTRAINT `PK_MAKER` -- 펀딩 메이커 기본키
        PRIMARY KEY (
                     `maker_id` -- 메이커 번호
            );

-- 제품
CREATE TABLE `PRODUCT`
(
    `product_id`    BIGINT       NOT NULL COMMENT '제품 번호',    -- 제품 번호
    `product_name`  VARCHAR(255) NOT NULL COMMENT '제품명',      -- 제품명
    `contents`      VARCHAR(255) NOT NULL COMMENT '제품 설명',    -- 제품 설명
    `price`         BIGINT       NOT NULL COMMENT '제품 가격',    -- 제품 가격
    `success_price` BIGINT       NOT NULL COMMENT '펀딩 성공 금액', -- 펀딩 성공 금액
    `max_quantity`  BIGINT       NOT NULL COMMENT '최대 판매 수량', -- 최대 판매 수량
    `success_rate`  BIGINT       NULL COMMENT '펀딩 성공률',       -- 펀딩 성공률
    `start_date`    DATETIME     NOT NULL COMMENT '펀딩 시작일',   -- 펀딩 시작일
    `end_date`      DATETIME     NOT NULL COMMENT '펀딩 종료일',   -- 펀딩 종료일
    `maker_id`      BIGINT       NOT NULL COMMENT '메이커 번호'    -- 메이커 번호
)
    COMMENT '제품';

-- 제품
ALTER TABLE `PRODUCT`
    ADD CONSTRAINT `PK_PRODUCT` -- 제품 기본키
        PRIMARY KEY (
                     `product_id` -- 제품 번호
            );

-- 팔로우
CREATE TABLE `FOLLOW`
(
    `follow_id` BIGINT NOT NULL COMMENT '팔로우 번호', -- 팔로우 번호
    `user_id`   BIGINT NOT NULL COMMENT '사용자 번호', -- 사용자 번호
    `maker_id`  BIGINT NOT NULL COMMENT '메이커 번호'  -- 메이커 번호
)
    COMMENT '팔로우';

-- 팔로우
ALTER TABLE `FOLLOW`
    ADD CONSTRAINT `PK_FOLLOW` -- 팔로우 기본키
        PRIMARY KEY (
                     `follow_id` -- 팔로우 번호
            );

-- 찜하기
CREATE TABLE `WISH`
(
    `wish_id`    BIGINT NOT NULL COMMENT '찜하기 번호', -- 찜하기 번호
    `user_id`    BIGINT NOT NULL COMMENT '사용자 번호', -- 사용자 번호
    `product_id` BIGINT NOT NULL COMMENT '제품 번호'   -- 제품 번호
)
    COMMENT '찜하기';

-- 찜하기
ALTER TABLE `WISH`
    ADD CONSTRAINT `PK_WISH` -- 찜하기 기본키
        PRIMARY KEY (
                     `wish_id` -- 찜하기 번호
            );

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