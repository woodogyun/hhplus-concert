package kr.hhplus.be.server.concert.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "concert")
@Builder
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 콘서트 ID (기본 키)

    @Column(nullable = false, length = 255)
    private String name; // 콘서트 이름

}