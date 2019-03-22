package pl.betse.beontime.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.betse.beontime.users.entity.TimeEntryEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntryEntity, Long> {
    List<TimeEntryEntity> findByUserGuidAndWeek(String guid, String week);
    Optional<TimeEntryEntity> findByUserGuid(String userGuid);
    Optional<TimeEntryEntity> findByProjectGuid(String projectGuid);

    @Query("SELECT entry FROM TimeEntryEntity entry WHERE entry.userGuid=:userGuid AND MONTH(entry.entryDate)=MONTH(:localDate) AND YEAR(entry.entryDate)=YEAR(:localDate)")
    List<TimeEntryEntity> findByUserGuidAndEntryDate(@Param("userGuid") String userGuid, @Param("localDate") LocalDate localDate);
}
