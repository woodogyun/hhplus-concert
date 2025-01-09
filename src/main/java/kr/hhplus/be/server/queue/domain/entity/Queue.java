package kr.hhplus.be.server.queue.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "queue")
@Builder
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키 및 순번 파악

    @Column(nullable = false, unique = true, name = "user_id")
    private Long userId; // 유저 아이디

    @Column(nullable = false, unique = true, name = "uuid")
    private String uuid; // UUID

    @Column(nullable = false, name = "schedule_id")
    private Long scheduleId; // 스케줄 ID

    @Column(name = "expires_at")
    private LocalDateTime expiresAt; // 만료 시간

    @Column(nullable = false)
    private String status; // 상태

//    @Column
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "position_seq")
//    @SequenceGenerator(name = "position_seq", sequenceName = "my_sequence", allocationSize = 1)
//    private Long position; // 위치

}