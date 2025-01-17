package abc.ignite.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import abc.ignite.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, String> {
    int countByClassEntityClassIdAndDate(String classId, LocalDate date);

    boolean existsByClassEntityClassIdAndUserUsernameAndDate(String classId, String username, LocalDate date);

    List<Booking> findByUserUsername(String username);

    List<Booking> findByDateBetween(LocalDate fromDate, LocalDate toDate);

    List<Booking> findByUserUsernameAndDateBetween(String username, LocalDate fromDate, LocalDate toDate);
}
