package by.sivko.miningrigservice.dao.config;

import by.sivko.miningrigservice.dao.GenericDao;
import by.sivko.miningrigservice.models.configs.MinerConfig;

public interface MinerConfigDao extends GenericDao<MinerConfig, Long>{
    MinerConfig deleteUserMinerConfigById(Long id);
}
