package pl.betse.beontime.timesheet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthDayBody {
    private String date;
    private BigDecimal hours;
    private String status;
    private String comment;
}
