package by.sivko.miningrigservice.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class MinerConfigDto {

    @Length(min = 3, message = "*Your configName must have at least 3 characters")
    @NotEmpty(message = "*Please provide your name")
    private String name;

    @NotNull
    private String commandLine;

    @NotNull
    private Long minerId;

    public MinerConfigDto(String name, String commandLine, Long minerId) {
        this.name = name;
        this.commandLine = commandLine;
        this.minerId = minerId;
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

    public Long getMinerId() {
        return minerId;
    }

    public void setMinerId(Long minerId) {
        this.minerId = minerId;
    }
}
