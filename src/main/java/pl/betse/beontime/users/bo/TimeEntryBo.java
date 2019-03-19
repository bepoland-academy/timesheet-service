package pl.betse.beontime.users.bo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TimeEntryBo {

    private String timeEntryId;
    private String projectGuid;
    private String userGuid;
    private BigDecimal hoursNumber;
    private String entryDate;
    private String comments;
    private String status;

}
