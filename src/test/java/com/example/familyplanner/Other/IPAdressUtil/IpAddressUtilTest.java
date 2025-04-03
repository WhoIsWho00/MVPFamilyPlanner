package com.example.familyplanner.Other.IPAdressUtil;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class IpAddressUtilTest {

    private final IpAddressUtil ipAddressUtil = new IpAddressUtil();

    @Test
    void testGetClientIpWithProxy() {
        // Arrange
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("x-forwarded-for")).thenReturn("192.168.1.1");

        // Act
        String clientIp = ipAddressUtil.getClientIp(request);

        // Assert
        assertThat(clientIp).isEqualTo("192.168.1.1");
    }

    @Test
    void testGetClientIpWithoutProxy() {
        // Arrange
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("x-forwarded-for")).thenReturn(null);
        Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        // Act
        String clientIp = ipAddressUtil.getClientIp(request);

        // Assert
        assertThat(clientIp).isEqualTo("127.0.0.1");
    }
}
