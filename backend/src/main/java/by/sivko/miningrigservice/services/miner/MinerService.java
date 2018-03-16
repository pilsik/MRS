package by.sivko.miningrigservice.services.miner;

import by.sivko.miningrigservice.models.miners.Miner;

import java.util.List;

public interface MinerService {
    Miner getMinerById(Long id);

    List<Miner> getAllMiners();

    Miner getMinerByName(String name);
}
