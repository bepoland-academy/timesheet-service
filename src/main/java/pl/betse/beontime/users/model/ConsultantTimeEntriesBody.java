package pl.betse.beontime.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantTimeEntriesBody {

    List<WeekTimeEntryBody> timeEntries;
}
