package pl.betse.beontime.timesheet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.betse.beontime.timesheet.validation.CreateTimeEntry;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthTimeEntryBody {

    @NotEmpty(groups = {CreateTimeEntry.class}, message = "ConsultantId can't be empty!")
    @NotNull(groups = {CreateTimeEntry.class}, message = "ConsultantId can't be null!")
    private String consultantId;

    @NotEmpty(groups = {CreateTimeEntry.class}, message = "ProjectId can't be empty!")
    @NotNull(groups = {CreateTimeEntry.class}, message = "ProjectId can't be null!")
    private String projectId;

    private String month;

    @NotEmpty(groups = {CreateTimeEntry.class}, message = "Month can't be empty!")
    @NotNull(groups = {CreateTimeEntry.class}, message = "Month can't be null!")
    @Size(groups = {CreateTimeEntry.class}, message = "Month list should have size between 1 nad 12!", min = 1, max = 12)
    private List<MonthDayBody> monthDays = new ArrayList<>();
}
