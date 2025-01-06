-- 사용자 정보를 저장하는 테이블
CREATE TABLE USER (
    id INT PRIMARY KEY,          -- 사용자 ID (기본 키)
    name VARCHAR(255)           -- 사용자 이름
);

-- 포인트 정보를 저장하는 테이블
CREATE TABLE POINT (
    id INT PRIMARY KEY,          -- 포인트 ID (기본 키)
    user_id INT,                -- 사용자 ID
    value INT,                  -- 포인트 값
    INDEX idx_user_id (user_id) -- 사용자 ID에 대한 인덱스
);

-- 콘서트 정보를 저장하는 테이블
CREATE TABLE CONCERT (
    id INT PRIMARY KEY,          -- 콘서트 ID (기본 키)
    name VARCHAR(255)           -- 콘서트 이름
);

-- 콘서트 일정 정보를 저장하는 테이블
CREATE TABLE CONCERT_SCHEDULE (
    id INT PRIMARY KEY,          -- 일정 ID (기본 키)
    concert_id INT,             -- 콘서트 ID
    start_date DATETIME,        -- 시작 날짜
    end_date DATETIME,          -- 종료 날짜
    play_date DATETIME,         -- 공연 날짜
    INDEX idx_concert_id (concert_id) -- 콘서트 ID에 대한 인덱스
);

-- 좌석 정보를 저장하는 테이블
CREATE TABLE SEAT (
    id INT PRIMARY KEY,          -- 좌석 ID (기본 키)
    seat_no VARCHAR(50),        -- 좌석 번호
    price FLOAT,                -- 좌석 가격
    schedule_id INT,            -- 일정 ID
    user_id INT,                -- 사용자 ID
    state VARCHAR(50),          -- 좌석 상태
    INDEX idx_schedule_id (schedule_id), -- 일정 ID에 대한 인덱스
    INDEX idx_user_id (user_id) -- 사용자 ID에 대한 인덱스
);

-- 예약 정보를 저장하는 테이블
CREATE TABLE RESERVATION (
    id INT PRIMARY KEY,          -- 예약 ID (기본 키)
    user_id INT,                -- 사용자 ID
    seat_id INT,                -- 좌석 ID
    expire_at DATETIME,         -- 만료 날짜
    INDEX idx_user_id (user_id), -- 사용자 ID에 대한 인덱스
    INDEX idx_seat_id (seat_id)  -- 좌석 ID에 대한 인덱스
);

-- 결제 정보를 저장하는 테이블
CREATE TABLE PAYMENT (
    id INT PRIMARY KEY,          -- 결제 ID (기본 키)
    user_id INT,                -- 사용자 ID
    seat_id INT,                -- 좌석 ID
    INDEX idx_user_id (user_id), -- 사용자 ID에 대한 인덱스
    INDEX idx_seat_id (seat_id)  -- 좌석 ID에 대한 인덱스
);

-- 토큰 정보를 저장하는 테이블
CREATE TABLE TOKEN (
    id INT PRIMARY KEY,          -- 토큰 ID (기본 키)
    user_id INT,                -- 사용자 ID
    concert_id INT,             -- 콘서트 ID
    expired_at DATETIME,        -- 만료 날짜
    INDEX idx_user_id (user_id), -- 사용자 ID에 대한 인덱스
    INDEX idx_concert_id (concert_id) -- 콘서트 ID에 대한 인덱스
);

CREATE TABLE queue (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 기본 키 및 순번 파악
    user_id BIGINT NOT NULL, -- 유저 ID
    uuid VARCHAR(255) NOT NULL UNIQUE, -- UUID
    schedule_id BIGINT NOT NULL, -- 스케줄 ID
    expires_at DATETIME, -- 만료 시간
    status VARCHAR(255) NOT NULL -- 상태
);

