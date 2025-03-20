package ru.netology.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalizationServiceImplTest {

    @ParameterizedTest
    @EnumSource(Country.class)
    void locale_shouldReturnRussianMessageForRussiaAndEnglishForOthers(Country country) {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

        String actualMessage = localizationService.locale(country);

        if (country == Country.RUSSIA) {
            assertEquals("Добро пожаловать", actualMessage);
        } else {
            assertEquals("Welcome", actualMessage);
        }
    }
}
