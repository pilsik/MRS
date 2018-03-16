package by.sivko.miningrigservice.services.user;

import by.sivko.miningrigservice.dao.user.UserProfileRepository;
import by.sivko.miningrigservice.dao.user.UserRepository;
import by.sivko.miningrigservice.models.configs.MinerConfig;
import by.sivko.miningrigservice.models.rigs.Rig;
import by.sivko.miningrigservice.models.user.User;
import by.sivko.miningrigservice.models.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        UserProfile userRole = this.userProfileRepository.findByType("ROLE_USER");
        user.setUserProfiles(new HashSet<>(Collections.singletonList(userRole)));
        this.userRepository.save(user);
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public List<Rig> getUserRigsByUsername(String username) {
        return this.findUserByUsername(username).getUserRigList();
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public void changeUserPassword(User user, String password) {
        user.setPassword(this.passwordEncoder.encode(password));
        this.userRepository.save(user);
    }

    @Override
    public List<MinerConfig> getUserMinerConfigsByUsername(String username) {
        return this.userRepository.findByUsername(username).getMinerConfigs();
    }
}
