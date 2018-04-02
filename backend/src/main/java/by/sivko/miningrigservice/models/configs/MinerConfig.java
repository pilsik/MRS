package by.sivko.miningrigservice.models.configs;

import by.sivko.miningrigservice.models.miners.Miner;
import by.sivko.miningrigservice.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "miner_configs")
public class MinerConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "miner_config_gen")
    @SequenceGenerator(name = "miner_config_gen", sequenceName = "miner_config_seq")
    private Long id;

    @Column
    @NotNull
    private String name;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @JsonIgnore
    @JoinColumn
    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Miner miner;

    @Column
    private String commandLine;

    public MinerConfig() {
    }

    public MinerConfig(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public MinerConfig(String name, User user, Miner miner) {
        this.name = name;
        this.user = user;
        this.miner = miner;
        this.commandLine = miner.getDefaultCommandLineWithParameters();
    }

    public void setMiner(Miner miner) {
        this.miner = miner;
        this.commandLine = miner.getDefaultCommandLineWithParameters();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Miner getMiner() {
        return miner;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public Long getId() {

        return id;
    }

    public String getCommandLine() {
        return commandLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MinerConfig that = (MinerConfig) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }
}
