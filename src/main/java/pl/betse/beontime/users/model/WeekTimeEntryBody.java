package pl.betse.beontime.users.model;

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
public class WeekTimeEntryBody {
    private String projectId;
    private String week;
    private List<WeekDayBody> weekDays = new ArrayList<>();
}
