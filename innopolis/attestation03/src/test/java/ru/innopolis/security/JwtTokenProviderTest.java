package ru.innopolis.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;
import ru.innopolis.utils.Constants;

import javax.crypto.SecretKey;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {

        SecretKey key = Jwts.SIG.HS512.key().build();
        String validBase64EncodedSecretKey = Encoders.BASE64.encode(key.getEncoded());
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", validBase64EncodedSecretKey);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationInMs", 3600000);
    }

    @Test
    void generateTokenValidAuthenticationReturnsToken() {
        UserPrincipal userPrincipal = new UserPrincipal(1L, "user", "pass",
                Collections.singletonList(new SimpleGrantedAuthority(Constants.DEFAULT_ROLE)));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null);

        String token = jwtTokenProvider.generateToken(authentication);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void validateTokenValidTokenReturnsTrue() {
        String token = jwtTokenProvider.generateToken(
                new UsernamePasswordAuthenticationToken(
                        new UserPrincipal(1L, "user", "pass",
                                Collections.singletonList(new SimpleGrantedAuthority(Constants.DEFAULT_ROLE))),
                        null));

        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void validateTokenInvalidTokenReturnsFalse() {
        assertFalse(jwtTokenProvider.validateToken("invalid.token.here"));
    }
}