package pl.betse.beontime.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.betse.beontime.users.entity.TimeEntryEntity;

@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntryEntity, Long> {
}
