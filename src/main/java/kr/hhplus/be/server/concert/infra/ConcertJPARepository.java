package kr.hhplus.be.server.concert.infra;

import kr.hhplus.be.server.concert.domain.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJPARepository extends JpaRepository<Concert, Long> {
}
