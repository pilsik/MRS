package by.sivko.miningrigservice.dao.config;

import by.sivko.miningrigservice.dao.GenericDaoImpl;
import by.sivko.miningrigservice.models.configs.MinerConfig;
import org.springframework.stereotype.Repository;

@Repository
public class MinerConfigDaoImpl extends GenericDaoImpl<MinerConfig, Long> implements MinerConfigDao {

    @Override
    public MinerConfig deleteUserMinerConfigById(Long id) {
        MinerConfig minerConfig = super.findOne(id);
        super.entityManager.remove(minerConfig);
        return minerConfig;
    }
}
