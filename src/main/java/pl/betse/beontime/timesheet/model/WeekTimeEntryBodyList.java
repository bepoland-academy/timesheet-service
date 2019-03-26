package pl.betse.beontime.timesheet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import pl.betse.beontime.timesheet.validation.CreateTimeEntry;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Validated(CreateTimeEntry.class)
@NoArgsConstructor
@Data
public class WeekTimeEntryBodyList {
    @NotNull(message = "Week days can't be null!")
    @NotEmpty(message = "Week days can't be empty!")
    List<@Valid WeekTimeEntryBody> weekTimeEntryBodyList = new ArrayList<>();
}
