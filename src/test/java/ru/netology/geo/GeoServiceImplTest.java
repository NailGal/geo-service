package ru.netology.geo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {

    @ParameterizedTest
    @MethodSource("ipAndLocationProvider")
    void byIp_shouldReturnCorrectLocationForGivenIp(String ip, Location expectedLocation) {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location actualLocation = geoService.byIp(ip);

        if (expectedLocation == null) {
            assertNull(actualLocation);
        } else {
            assertNotNull(actualLocation);
            assertEquals(expectedLocation.getCity(), actualLocation.getCity());
            assertEquals(expectedLocation.getCountry(), actualLocation.getCountry());
            assertEquals(expectedLocation.getStreet(), actualLocation.getStreet());
            assertEquals(expectedLocation.getBuiling(), actualLocation.getBuiling());
        }
    }

    private static Stream<Arguments> ipAndLocationProvider() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.1.2.3", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.1.2.3", new Location("New York", Country.USA, null, 0)),
                Arguments.of("10.0.0.1", null)
        );
    }

    @Test
    void byCoordinates_shouldThrowRuntimeException() {
        GeoServiceImpl geoService = new GeoServiceImpl();

        assertThrows(RuntimeException.class, () -> geoService.byCoordinates(0, 0));
    }
}
