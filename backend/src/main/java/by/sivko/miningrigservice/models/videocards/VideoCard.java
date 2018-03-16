package by.sivko.miningrigservice.models.videocards;

import by.sivko.miningrigservice.models.status.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * class VideoCard
 */
@Entity
@Table(name = "video_cards")
public abstract class VideoCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_card_gen")
    @SequenceGenerator(name = "video_card_gen", sequenceName = "video_card_seq")
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    protected Status status;

    @Column
    protected String name;

    @Column
    protected int temperature;

    @Column
    protected int power;

    @Column
    protected int memory;

    //constructors
    private VideoCard() {
    }

    public VideoCard(String name, int temperature, int power, int memory) {
        this();
        this.name = name;
        this.temperature = temperature;
        this.power = power;
        this.memory = memory;
    }

    //getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getMemory() {
        return memory;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getPower() {
        return power;
    }

}
