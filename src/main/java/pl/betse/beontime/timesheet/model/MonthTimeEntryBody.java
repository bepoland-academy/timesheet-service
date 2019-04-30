package pl.betse.beontime.timesheet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import pl.betse.beontime.timesheet.validation.CreateTimeEntry;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated(CreateTimeEntry.class)
public class MonthTimeEntryBody {

    @NotEmpty(groups = {CreateTimeEntry.class}, message = "ConsultantId can't be empty!")
    @NotNull(groups = {CreateTimeEntry.class}, message = "ConsultantId can't be null!")
    private String consultantId;

    @NotEmpty(groups = {CreateTimeEntry.class}, message = "ProjectId can't be empty!")
    @NotNull(groups = {CreateTimeEntry.class}, message = "ProjectId can't be null!")
    private String projectId;

    private String month;

    private Integer approvedHours;

    private boolean offSite;

    @NotEmpty(groups = {CreateTimeEntry.class}, message = "Month can't be empty!")
    @NotNull(groups = {CreateTimeEntry.class}, message = "Month can't be null!")
    @Size(groups = {CreateTimeEntry.class}, message = "Month list should have size between 1 nad 31!", min = 1, max = 31)
    private List<@Valid MonthDayBody> monthDays = new ArrayList<>();
}
