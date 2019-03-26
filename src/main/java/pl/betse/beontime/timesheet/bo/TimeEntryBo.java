package pl.betse.beontime.timesheet.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeEntryBo)) return false;
        TimeEntryBo that = (TimeEntryBo) o;
        return Objects.equals(getProjectGuid(), that.getProjectGuid()) &&
                Objects.equals(getUserGuid(), that.getUserGuid()) &&
                Objects.equals(getEntryDate(), that.getEntryDate()) &&
                Objects.equals(getWeek(), that.getWeek());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectGuid(), getUserGuid(), getEntryDate(), getWeek());
    }
}
