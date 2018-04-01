package by.sivko.miningrigservice.models.miners;

import javax.persistence.*;

@Entity
@Table(name = "miners")
public class Miner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "miner_gen")
    @SequenceGenerator(name = "miner_gen", sequenceName = "miner_seq")
    private Long id;

    @Column
    private String name;

    @Column(name = "path_to_exe_file")
    private String PATH_TO_EXE_FILE;

    @Column
    private String defaultCommandLineWithParameters;

    public Miner() {
    }

    public Miner(String name, String PATH_TO_EXE_FILE, String defaultCommandLineWithParameters, String version) {
        this.name = name;
        this.PATH_TO_EXE_FILE = PATH_TO_EXE_FILE;
        this.defaultCommandLineWithParameters = defaultCommandLineWithParameters;
        this.version = version;
    }

    @Column
    private String version;

    public String getPATH_TO_EXE_FILE() {
        return PATH_TO_EXE_FILE;
    }

    public String getDefaultCommandLineWithParameters() {
        return defaultCommandLineWithParameters;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
