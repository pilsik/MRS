package by.sivko.miningrigservice.dao.miner;

import by.sivko.miningrigservice.models.miners.Miner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinerDao extends JpaRepository<Miner, Long> {
    Miner findByName(String name);
}
