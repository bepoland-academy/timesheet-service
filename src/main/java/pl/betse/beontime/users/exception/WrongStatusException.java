package pl.betse.beontime.users.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class WrongStatusException extends RuntimeException {

    private String additionalMessage;

    public WrongStatusException(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }
}
