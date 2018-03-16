package by.sivko.miningrigservice.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class MinerConfigDto {

    @Length(min = 3, message = "*Your configName must have at least 3 characters")
    @NotEmpty(message = "*Please provide your name")
    private String name;

    @NotEmpty(message = "*Please provide your commandLine")
    private String commandLine;

    public MinerConfigDto(String name, String commandLine) {
        this.name = name;
        this.commandLine = commandLine;
    }

    public MinerConfigDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }
}
