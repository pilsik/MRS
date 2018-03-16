package by.sivko.miningrigservice.models.rigs;

import by.sivko.miningrigservice.models.status.Status;
import by.sivko.miningrigservice.models.configs.MinerConfig;
import by.sivko.miningrigservice.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * class Rig
 */
@Entity
@Table(name = "rigs")
public class Rig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rig_gen")
    @SequenceGenerator(name = "rig_gen", sequenceName = "rig_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "rig", cascade = CascadeType.ALL)
    private Status status;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn
    private MinerConfig minerConfig;

    //constructors
    public Rig() {
    }

    public Rig(String name, String password, User user) {
        this.user = user;
        this.name = name;
        this.password = password;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public Status getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MinerConfig getMinerConfig() {
        return minerConfig;
    }

    public void setMinerConfig(MinerConfig minerConfig) {
        this.minerConfig = minerConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rig rig = (Rig) o;

        if (!name.equals(rig.name)) return false;
        if (!password.equals(rig.password)) return false;
        return user.equals(rig.user);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

}
