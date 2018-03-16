package by.sivko.miningrigservice.services.configs;

import by.sivko.miningrigservice.models.configs.MinerConfig;
import by.sivko.miningrigservice.models.miners.Miner;

public interface MinerConfigService {

    MinerConfig getMinerConfigById(Long id);

    void removeMinerConfigById(Long id);

    void addMinerConfig(MinerConfig minerConfig);

    void removeMinerConfig(MinerConfig minerConfig);

}
