package pl.betse.beontime.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.betse.beontime.timesheet.entity.MonthEntity;
import pl.betse.beontime.timesheet.entity.StatusEntity;
import pl.betse.beontime.timesheet.entity.TimeEntryEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntryEntity, Long> {

    List<TimeEntryEntity> findByUserGuidAndWeekOrderByEntryDate(String userGuid, String weekNumber);

    List<TimeEntryEntity> findByUserGuidAndProjectGuidAndWeekOrderByEntryDate(String userGuid, String projectGuid, String weekNumber);

    boolean existsByUserGuidAndProjectGuidAndEntryDate(String userGuid, String projectGuid, LocalDate entryDate);

    boolean existsByProjectGuid(String projectGuid);

    boolean existsByUserGuid(String userGuid);

    @Query("SELECT entry FROM TimeEntryEntity entry WHERE entry.userGuid=:userGuid AND MONTH(entry.entryDate)=MONTH(:localDate) AND YEAR(entry.entryDate)=YEAR(:localDate) ORDER BY entry.entryDate")
    List<TimeEntryEntity> findByUserGuidAndMonth(@Param("userGuid") String userGuid, @Param("localDate") LocalDate localDate);

    @Query("SELECT entry FROM TimeEntryEntity entry WHERE entry.userGuid=:userGuid AND MONTH(entry.entryDate)=MONTH(:localDate) AND YEAR(entry.entryDate)=YEAR(:localDate) AND entry.projectGuid=:projectGuid ORDER BY entry.entryDate")
    List<TimeEntryEntity> findByUserGuidAndProjectGuidAndMonth(@Param("userGuid") String userGuid, @Param("projectGuid") String projectGuid, @Param("localDate") LocalDate localDate);

    @Query("SELECT new pl.betse.beontime.timesheet.entity.MonthEntity(DATE_FORMAT(entry.entryDate, '%Y-%m')) FROM TimeEntryEntity entry " +
            "WHERE entry.userGuid=:userGuid AND entry.statusEntity=:status " +
            "GROUP BY DATE_FORMAT(entry.entryDate, '%Y-%m')")
    List<MonthEntity> findMonthsByStatusAndUserGuid(@Param("userGuid") String userGuid, @Param("status") StatusEntity status);

    @Query("SELECT SUM (entry.hoursNumber)FROM TimeEntryEntity entry " +
            "WHERE entry.userGuid=:userGuid AND entry.statusEntity=:status ")
    Integer getHoursByUserAndStatus(@Param("userGuid") String userGuid, @Param("status") StatusEntity status);
}