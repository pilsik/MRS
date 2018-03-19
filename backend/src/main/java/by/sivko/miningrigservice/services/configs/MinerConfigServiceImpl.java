package by.sivko.miningrigservice.services.configs;

import by.sivko.miningrigservice.dao.config.MinerConfigDao;
import by.sivko.miningrigservice.models.configs.MinerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MinerConfigServiceImpl implements MinerConfigService {


    private final MinerConfigDao minerConfigDao;

    @Autowired
    public MinerConfigServiceImpl(MinerConfigDao minerConfigDao) {
        this.minerConfigDao = minerConfigDao;
    }

    @Override
    public MinerConfig getMinerConfigById(Long id) {
        return this.minerConfigDao.findOne(id);
    }

    @Override
    public void removeMinerConfigById(Long id) {
        this.minerConfigDao.deleteUserMinerConfigById(id);
    }

    @Override
    public void addMinerConfig(MinerConfig minerConfig) {
        this.minerConfigDao.save(minerConfig);
    }

    @Override
    public void removeMinerConfig(MinerConfig minerConfig) {
        this.minerConfigDao.remove(minerConfig);
    }
}
