package uz.softcity.backbuild.buildmegaservice.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.softcity.backbuild.buildmegaservice.entity.Role;
import uz.softcity.backbuild.buildmegaservice.entity.User;
import uz.softcity.backbuild.buildmegaservice.entity.utils.AppConstants;
import uz.softcity.backbuild.buildmegaservice.repository.RoleRepository;
import uz.softcity.backbuild.buildmegaservice.repository.UserRepository;

import static uz.softcity.backbuild.buildmegaservice.entity.enums.Permission.*;

import java.util.Arrays;
import java.util.HashSet;

/**
 * this class is for creating initial data to database
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    /**
     * When Launches the application first this class is running
     * @param args - Application startup arguments
     */
    @Override
    public void run(String... args) {
        if (initialMode.equals("always")) {
            Role admin = roleRepository.save(new Role(AppConstants.ADMIN, "system admin", new HashSet<>(Arrays.asList(values()))));
            userRepository.save(new User("admin_name", passwordEncoder.encode("admin_password"), admin));
        }
    }
}
