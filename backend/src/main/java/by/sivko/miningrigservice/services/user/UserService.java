package by.sivko.miningrigservice.services.user;

import by.sivko.miningrigservice.models.configs.MinerConfig;
import by.sivko.miningrigservice.models.rigs.Rig;
import by.sivko.miningrigservice.models.user.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User saveUser(User user);

    List<Rig> getUserRigsByUsername(String username);

    List<MinerConfig> getUserMinerConfigsByUsername(String username);

    void changeUserPassword(User user, String password);

}
