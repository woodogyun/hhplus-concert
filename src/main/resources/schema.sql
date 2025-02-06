-- 사용자 정보를 저장하는 테이블
CREATE TABLE user (
    id INT PRIMARY KEY,          -- 사용자 ID (기본 키)
    name VARCHAR(255)           -- 사용자 이름
);

-- 포인트 정보를 저장하는 테이블
CREATE TABLE point (
    id INT PRIMARY KEY,          -- 포인트 ID (기본 키)
    user_id INT,                -- 사용자 ID
    value INT,                  -- 포인트 값
    INDEX idx_user_id (user_id) -- 사용자 ID에 대한 인덱스
);

-- 콘서트 정보를 저장하는 테이블
CREATE TABLE concert (
    id INT PRIMARY KEY,          -- 콘서트 ID (기본 키)
    name VARCHAR(255)           -- 콘서트 이름
);

-- 콘서트 일정 정보를 저장하는 테이블
CREATE TABLE concert_schedule (
      id INT PRIMARY KEY,                          -- 일정 ID (기본 키)
      concert_id INT,                             -- 콘서트 ID
      reservation_start_at DATETIME,                 -- 예매 시작 날짜
      reservation_end_at DATETIME,                   -- 예매 종료 날짜
      actual_performance_date DATETIME,           -- 실제 공연이 시작하는 날짜
      INDEX idx_concert_id (concert_id)          -- 콘서트 ID에 대한 인덱스
);
-- 좌석 정보를 저장하는 테이블
CREATE TABLE seat (
    id INT PRIMARY KEY,         -- 좌석 ID (기본 키)
    seat_no VARCHAR(50),        -- 좌석 번호
    price INT,                  -- 좌석 가격
    schedule_id INT,            -- 일정 ID
    user_id INT,                -- 사용자 ID
    state VARCHAR(50),          -- 좌석 상태
    INDEX idx_schedule_id (schedule_id), -- 일정 ID에 대한 인덱스
    INDEX idx_user_id (user_id) -- 사용자 ID에 대한 인덱스
);

-- 예약 정보를 저장하는 테이블
CREATE TABLE reservation (
    id INT PRIMARY KEY,          -- 예약 ID (기본 키)
    user_id INT,                -- 사용자 ID
    seat_id INT,                -- 좌석 ID
    state VARCHAR(50),          -- 좌석 상태
    expire_at DATETIME,         -- 만료 날짜
    INDEX idx_user_id (user_id), -- 사용자 ID에 대한 인덱스
    INDEX idx_seat_id (seat_id)  -- 좌석 ID에 대한 인덱스
);

-- 결제 정보를 저장하는 테이블
CREATE TABLE payment (
    id INT PRIMARY KEY,          -- 결제 ID (기본 키)
    user_id INT,                -- 사용자 ID
    seat_id INT,                -- 좌석 ID
    INDEX idx_user_id (user_id), -- 사용자 ID에 대한 인덱스
    INDEX idx_seat_id (seat_id)  -- 좌석 ID에 대한 인덱스
);

CREATE TABLE queue (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 기본 키 및 순번 파악
    user_id BIGINT NOT NULL, -- 유저 ID
    uuid VARCHAR(255) NOT NULL UNIQUE, -- UUID
    schedule_id BIGINT NOT NULL, -- 스케줄 ID
    expires_at DATETIME, -- 만료 시간
    state VARCHAR(255) NOT NULL -- 상태
);

