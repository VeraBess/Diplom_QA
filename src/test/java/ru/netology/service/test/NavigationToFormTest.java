package ru.netology.service.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.service.page.JourneyOfTheDay;

import static com.codeborne.selenide.Condition.visible;

class NavigationToFormsTests {

    private JourneyOfTheDay journeyPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        journeyPage = new JourneyOfTheDay();
    }

    @Test
    @DisplayName("Проверка открытия главной страницы")
    void shouldOpenHomepage() {
        journeyPage.openHeadPage();// открываем главную страницу
        journeyPage.travelTitle.shouldBe(visible);
        journeyPage.buyButton.shouldBe(visible);
        journeyPage.buyCreditButton.shouldBe(visible);
    }

    @Test
    @DisplayName("Переход к форме оплаты по карте")
    void shouldNavigateToPaymentForm() {
        journeyPage.openHeadPage(); // Открываем главную страницу
        journeyPage.clickBuyButton(); // Нажимаем на кнопку "Купить"

        journeyPage.paymentByCard.shouldBe(visible);
    }

    @Test
    @DisplayName("Переход к форме оформления кредита по данным карты")
    void shouldNavigateToCreditForm() {
        journeyPage.openHeadPage(); // Открываем главную страницу
        journeyPage.clickBuyCreditButton(); // Нажимаем на кнопку "Купить в кредит"

        journeyPage.creditToCardData.shouldBe(visible);
    }
}

