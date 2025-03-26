package com.example.familyplanner.Security.JWT;

import com.example.familyplanner.service.exception.InvalidJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtCoreTest {

    private JwtCore jwtCore;
    private final String testSecret = "testSecretKeyThatIsLongEnoughForHmacSHA256Algorithm";
    private final long testLifeTime = 3600000; // 1 hour
    private final String testEmail = "test@example.com";

    @BeforeEach
    void setUp() {
        jwtCore = new JwtCore();
        ReflectionTestUtils.setField(jwtCore, "jwtSecret", testSecret);
        ReflectionTestUtils.setField(jwtCore, "jwtLifeTime", testLifeTime);
    }

    @Test
    void createToken_ShouldGenerateValidToken() {

        String token = jwtCore.createToken(testEmail);


        assertNotNull(token);
        assertTrue(token.length() > 0);
        assertTrue(jwtCore.validateToken(token));
        assertEquals(testEmail, jwtCore.getUserNameFromJwt(token));
    }

    @Test
    void validateToken_ShouldReturnTrueForValidToken() {

        String token = jwtCore.createToken(testEmail);


        assertTrue(jwtCore.validateToken(token));
    }

    @Test
    void validateToken_ShouldThrowExceptionForInvalidToken() {

        String invalidToken = "invalidToken";


        assertThrows(InvalidJwtException.class, () -> jwtCore.validateToken(invalidToken));
    }

    @Test
    void validateToken_ShouldThrowExceptionForExpiredToken() {

        JwtCore expiredJwtCore = new JwtCore();
        ReflectionTestUtils.setField(expiredJwtCore, "jwtSecret", testSecret);
        ReflectionTestUtils.setField(expiredJwtCore, "jwtLifeTime", -10000);

        String expiredToken = expiredJwtCore.createToken(testEmail);


        assertThrows(InvalidJwtException.class, () -> jwtCore.validateToken(expiredToken));
    }

    @Test
    void getUserNameFromJwt_ShouldReturnCorrectEmail() {

        String token = jwtCore.createToken(testEmail);

        String result = jwtCore.getUserNameFromJwt(token);

        assertEquals(testEmail, result);
    }

    @Test
    void getUserNameFromJwt_ShouldThrowExceptionForInvalidToken() {

        String invalidToken = "invalidToken";

        Exception exception = assertThrows(Exception.class, () ->
                jwtCore.getUserNameFromJwt(invalidToken)
        );

        assertTrue(exception instanceof RuntimeException);
    }
}