package by.sivko.miningrigservice.dao.rig;

import by.sivko.miningrigservice.dao.GenericDao;
import by.sivko.miningrigservice.models.rigs.Rig;

public interface RigDao extends GenericDao<Rig,Long> {

    Rig deleteRigById(Long id);

    void updateRig(Rig rig);

}
