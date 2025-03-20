package ru.netology.sender;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class MessageSenderImplTest {

    @ParameterizedTest
    @CsvSource({
            "172.123.45.67, Добро пожаловать",
            "96.44.183.149, Welcome",
            "10.0.0.1, Welcome"
    })
    void send_shouldReturnCorrectMessageBasedOnIp(String ip, String expectedMessage) {

        // Arrange
        GeoService geoService = Mockito.mock(GeoService.class);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);

        // Настраиваем GeoService
        Mockito.when(geoService.byIp(ip)).thenAnswer(invocation -> {
            if (ip.startsWith("172.")) {
                return new Location("Moscow", Country.RUSSIA, null, 0);
            } else if (ip.startsWith("96.")) {
                return new Location("New York", Country.USA, null, 0);
            }
            return new Location(null, Country.USA, null, 0);
        });

        // Настраиваем LocalizationService
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSenderImpl sender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        // Act
        String result = sender.send(headers);

        // Assert
        assertEquals(expectedMessage, result);

        /*// Создание заглушек
        GeoService geoService = Mockito.mock(GeoService.class);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);

        Mockito.when(geoService.byIp(anyString())).thenAnswer(invocation -> {
            String ipArg = invocation.getArgument(0);
            if (ipArg.startsWith("172.")) {
                return new Location("Moscow", Country.RUSSIA, null, 0);
            } else if (ipArg.startsWith("96.")) {
                return new Location("New York", Country.USA, null, 0);
            } else {
                return new Location(null, Country.GERMANY, null, 0);
            }
        });

        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(any(Country.class))).thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        String actualMessage = messageSender.send(headers);

        assertEquals(expectedMessage, actualMessage);
    }*/
    }
}
