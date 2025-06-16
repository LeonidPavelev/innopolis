package ru.innopolis.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.innopolis.entity.User;
import ru.innopolis.repository.UserRepository;
import ru.innopolis.security.UserPrincipal;

import java.util.stream.Collectors;
/**
 * Сервис для аутентификации и авторизации пользователей.
 * Реализует интерфейс UserDetailsService для загрузки данных пользователя.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Загружает данные пользователя по имени пользователя для целей аутентификации.
     *
     * @param username имя пользователя для поиска
     * @return объект UserDetails с информацией о пользователе
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Looking for user with username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new UsernameNotFoundException("User not found");
                });
        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
