package pl.betse.beontime.users.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class WrongStatusException extends RuntimeException {

    String additionalMessage;

    public WrongStatusException(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }
}
