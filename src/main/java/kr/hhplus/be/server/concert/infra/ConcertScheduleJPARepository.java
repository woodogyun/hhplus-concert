package kr.hhplus.be.server.concert.infra;

import kr.hhplus.be.server.concert.domain.entity.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConcertScheduleJPARepository extends JpaRepository<ConcertSchedule, Long> {

    @Query("SELECT cs FROM concert_schedule cs WHERE cs.concertId = :concertId " +
            "AND cs.reservationStartAt <= :currentDate " +
            "AND cs.reservationEndAt >= :currentDate")
    List<ConcertSchedule> findAvailableConcertSchedules(@Param("concertId") long concertId, @Param("currentDate") LocalDateTime currentDate);

}
