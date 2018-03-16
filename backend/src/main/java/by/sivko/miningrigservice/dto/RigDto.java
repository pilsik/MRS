package by.sivko.miningrigservice.dto;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class RigDto {

    @Length(min = 3, message = "*Your nameRig must have at least 3 characters")
    @NotEmpty(message = "*Please provide your name")
        private String name;

    @Length(min = 3, message = "*Your password must have at least 3 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    public RigDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public RigDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
