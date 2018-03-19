package by.sivko.miningrigservice.services.miner;

import by.sivko.miningrigservice.dao.miner.MinerDao;
import by.sivko.miningrigservice.models.miners.Miner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MinerServiceImpl implements MinerService{


    private final MinerDao minerDao;

    @Autowired
    public MinerServiceImpl(MinerDao minerDao) {
        this.minerDao = minerDao;
    }

    @Override
    public Miner getMinerById(Long id) {
        return this.minerDao.findOne(id);
    }

    @Override
    public List<Miner> getAllMiners() {
        return this.minerDao.findAll();
    }

    @Override
    public Miner getMinerByName(String name) {
        return this.minerDao.findByName(name);
    }
}
