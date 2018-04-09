package by.sivko.miningrigservice.services.rig;

import by.sivko.miningrigservice.models.rigs.Rig;

public interface RigService {

    void addRig(Rig rig);

    Rig getRigById(Long id);

    void removeRigById(Long id);

    void removeRig(Rig rig);

    void updateRig(Rig rig);
}
