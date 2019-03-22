package pl.betse.beontime.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import pl.betse.beontime.users.validation.CreateTimeEntry;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeekDayBody {

    private String day;

    @NotEmpty(groups = {CreateTimeEntry.class}, message = "Date can't be empty!")
    @NotNull(groups = {CreateTimeEntry.class}, message = "Date can't be null!")
    private String date;

    @NotNull(groups = {CreateTimeEntry.class}, message = "Hours can't be null!")
    @PositiveOrZero(groups = {CreateTimeEntry.class}, message = "Hours value should be equal or bigger than 0.")
    @Max(groups = {CreateTimeEntry.class}, message = "Hours value should be equal or smaller than 24.", value = 24)
    private BigDecimal hours;

    @NotEmpty(groups = {CreateTimeEntry.class}, message = "Status can't be empty!")
    @NotNull(groups = {CreateTimeEntry.class}, message = "Status can't be null!")
    private String status;

    @Length(groups = {CreateTimeEntry.class}, max = 500, message = "Comments length should be between 0 and 500 characters.")
    @NotNull(groups = {CreateTimeEntry.class}, message = "Comment can't be null!")
    private String comment;
}
