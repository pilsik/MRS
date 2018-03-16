package by.sivko.miningrigservice.dao.user;

import by.sivko.miningrigservice.models.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile findByType(String type);
}