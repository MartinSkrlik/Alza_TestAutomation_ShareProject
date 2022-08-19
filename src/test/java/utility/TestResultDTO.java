package utility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestResultDTO {
    private int scenariosTotal;
    private int scenariosPassed;
    private int scenariosFailed;
}