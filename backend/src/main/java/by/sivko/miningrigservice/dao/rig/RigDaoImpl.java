package by.sivko.miningrigservice.dao.rig;

import by.sivko.miningrigservice.dao.GenericDaoImpl;
import by.sivko.miningrigservice.models.rigs.Rig;
import org.springframework.stereotype.Repository;

@Repository
public class RigDaoImpl extends GenericDaoImpl<Rig,Long> implements RigDao {

    @Override
    public Rig deleteRigById(Long id) {
        Rig deletedRig = this.findOne(id);
        super.entityManager.remove(deletedRig);
        return deletedRig;
    }

}
