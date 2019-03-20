package pl.betse.beontime.users.model;

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
public class WeekDayBody {
    private String day;
    private String date;
    private BigDecimal hours;
    private String status;
    private String comment;


//    public WeekDayBody(String date, BigDecimal hours, String status, String comment) {
//        this.date = LocalDate.parse(date);
//        this.day = this.date.getDayOfWeek().name();
//        this.hours = hours;
//        this.status = status;
//        this.comment = comment;
//    }

}
