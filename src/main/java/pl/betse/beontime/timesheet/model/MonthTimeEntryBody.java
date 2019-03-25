package pl.betse.beontime.timesheet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthTimeEntryBody {

    private String consultantId;
    private String projectId;
    private String month;
    private List<MonthDayBody> monthDays = new ArrayList<>();
}
