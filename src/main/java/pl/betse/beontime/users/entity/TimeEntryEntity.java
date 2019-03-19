package pl.betse.beontime.users.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "TIME_ENTRY")
public class TimeEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIME_ENTRY_ID")
    private Long id;

    @Column(name = "TIME_ENTRY_GUID", nullable = false, unique = true)
    private String guid;

    @Column(name = "PROJECT_GUID", nullable = false, unique = true)
    private String projectGuid;

    @Column(name = "USER_GUID", nullable = false, unique = true)
    private String userGuid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "STATUS_ID")
    StatusEntity statusEntity;

    @Column(name = "HOURS_NUMBER")
    private BigDecimal hoursNumber;

    @Column(name = "DATE")
    private LocalDate entryDate;

    @Length(max = 500)
    @Column(name = "COMMENTS")
    private String comments;


}
