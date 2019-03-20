package pl.betse.beontime.users.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeEntryBo {

    private String timeEntryId;
    private String projectGuid;
    private String userGuid;
    private BigDecimal hoursNumber;
    private LocalDate entryDate;
    private String comment;
    private String status;
    private String week;

}
