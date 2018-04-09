package by.sivko.miningrigservice.models.status;

import by.sivko.miningrigservice.models.rigs.Rig;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "status")
public class Status implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_gen")
    @SequenceGenerator(name = "status_gen", sequenceName = "status_seq")
    private Long id;

    @JsonIgnore
    @JoinColumn(nullable = false)
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Rig rig;

    @Column
    private boolean online = false;

    @Column
    private boolean needReboot = false;

    @Column
    private String stats;

   /* @OneToMany(fetch = FetchType.EAGER, mappedBy = "status", cascade = CascadeType.ALL)
    private List<VideoCard> cardList;*/

    public Status() {
    }

    public Long getId() {
        return id;
    }

    public Rig getRig() {
        return rig;
    }

    public void setRig(Rig rig) {
        this.rig = rig;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isNeedReboot() {
        return needReboot;
    }

    public void setNeedReboot(boolean needReboot) {
        this.needReboot = needReboot;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    /*  public List<VideoCard> getCardList() {
        return cardList;
    }

    public void setCardList(List<VideoCard> cardList) {
        this.cardList = cardList;
    }*/

}
