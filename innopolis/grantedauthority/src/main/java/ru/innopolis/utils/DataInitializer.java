package ru.innopolis.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.innopolis.model.Role;
import ru.innopolis.model.User;
import ru.innopolis.repository.RoleRepository;
import ru.innopolis.repository.UserRepository;

import java.util.Set;

@Component
public class DataInitializer {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        // Create roles
        Role viewerRole = createRoleIfNotFound("ROLE_VIEWER");
        Role userRole = createRoleIfNotFound("ROLE_USER");
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN");

        // Create users
        createUserIfNotFound("viewer", "viewerpass", viewerRole);
        createUserIfNotFound("user", "userpass", userRole);
        createUserIfNotFound("admin", "adminpass", adminRole);

        System.out.println("Created users:");
        System.out.println("viewer/" + passwordEncoder.encode("viewerpass"));
        System.out.println("user/" + passwordEncoder.encode("userpass"));
        System.out.println("admin/" + passwordEncoder.encode("adminpass"));
    }

    private Role createRoleIfNotFound(String name) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(name);
                    return roleRepository.save(role);
                });
    }

    private void createUserIfNotFound(String username, String password, Role role) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles(Set.of(role));
            userRepository.save(user);
        }
    }
}