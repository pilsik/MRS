package by.sivko.miningrigservice.dto;

public class MinerDto {

    private long id;

    public MinerDto(long id) {
        this.id = id;
    }

    public MinerDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
