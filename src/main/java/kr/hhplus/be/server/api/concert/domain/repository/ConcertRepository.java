package kr.hhplus.be.server.api.concert.domain.repository;

import kr.hhplus.be.server.api.concert.domain.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
}
