package pl.betse.beontime.timesheet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
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
public class WeekTimeEntryBody extends ResourceSupport {

    @NotEmpty(groups = {CreateTimeEntry.class}, message = "ProjectId can't be empty!")
    @NotNull(groups = {CreateTimeEntry.class}, message = "ProjectId can't be null!")
    private String projectId;

    private String week;

    private Integer approvedHours;

    private boolean offSite;

    @NotEmpty(groups = {CreateTimeEntry.class}, message = "Week days can't be empty!")
    @NotNull(groups = {CreateTimeEntry.class}, message = "Week days can't be null!")
    @Size(groups = {CreateTimeEntry.class}, message = "Week list should have size between 1 nad 7!", min = 1, max = 7)
    private List<@Valid WeekDayBody> weekDays = new ArrayList<>();
}
