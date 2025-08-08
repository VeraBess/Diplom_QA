package ru.netology.service.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.service.data.DataHelper;
import ru.netology.service.page.JourneyOfTheDay;

public class JourneyOfTheDayTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    JourneyOfTheDay journeyPage = new JourneyOfTheDay();
    private DataHelper.CardData validCardData;
    private DataHelper.CardData inValidCardData;

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:8080");
        journeyPage.clickBuyButton();
        validCardData = DataHelper.getValidCardData(); // получаем объект с валидными данными карты
        inValidCardData = DataHelper.getBlockedCardData(); // получаем объект с заблокированной картой
    }

    // ВАЛИДНЫЕ ДАННЫЕ
    @Test
    @DisplayName("Покупка тура через кнопку Купить с валидными данными")
    void buyTourViaButtonBuyWithValidData() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc()); // заполняем форму данными
        journeyPage.continueButton(); // нажимаем кнопку "Продолжить"
        journeyPage.checkOkMessage("Операция одобрена Банком."); // проверяем успешное выполнение операции
    }

    @Test
    @DisplayName("Покупка тура через кнопку Купить в кредит с валидными данными")
    void buyTourViaButtonBuyCreditWithValidData() {
        journeyPage.clickBuyCreditButton(); // кликаем по кнопке "Купить в кредит"
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkOkMessage("Операция одобрена Банком.");
    }

    // НЕВАЛИДНЫЕ ДАННЫЕ
    @Test
    @DisplayName("Покупка тура через кнопку Купить с заблокированной картой")
    void buyTourViaButtonBuyWithBlockedCard() {
        journeyPage.fillPaymentForm(inValidCardData.getNumber(),
                inValidCardData.getMonth(),
                inValidCardData.getYear(),
                inValidCardData.getOwner(),
                inValidCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorMessage("Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    @DisplayName("Покупка тура через кнопку Купить в кредит с заблокированной картой")
    void buyTourViaButtonBuyCreditWithBlockedCard() {
        journeyPage.clickBuyCreditButton(); // кликаем по кнопке "Купить в кредит"
        journeyPage.fillPaymentForm(inValidCardData.getNumber(),
                inValidCardData.getMonth(),
                inValidCardData.getYear(),
                inValidCardData.getOwner(),
                inValidCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorMessage("Ошибка! Банк отказал в проведении операции.");
    }

    // ПОЛЯ ФОРМЫ
    // НОМЕР КАРТЫ
    @Test
    @DisplayName("Пустое поле номер карты")
    void fieldNumberCardEmpty() {
        journeyPage.fillPaymentForm("",
                validCardData.getMonth(),
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    @Test
    @DisplayName("Поле номер карты одна цифра")
    void fieldNumberCardOneNumber() {
        journeyPage.fillPaymentForm("1",
                validCardData.getMonth(),
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    @Test
    @DisplayName("Поле номер карты 15 цифр")
    void fieldNumberCardFifteenNumber() {
        journeyPage.fillPaymentForm("1111 2222 3333 444",
                validCardData.getMonth(),
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    @Test //!!!!! спросить про баг или нет?
    @DisplayName("Поле номер карты 17 цифр")
    void fieldNumberCardSeventeenNumber() {
        journeyPage.fillPaymentForm("1111 2222 3333 4444 5",
                validCardData.getMonth(),
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.checkCardNumber("1111 2222 3333 4444"); //проверка что в поле только 16 цифр
        journeyPage.continueButton();
        journeyPage.checkOkMessage("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("Поле номер карты буквы")
    void fieldNumberCardLetters() {
        journeyPage.fillPaymentForm("аааа аааа аааа аааа",
                validCardData.getMonth(),
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.checkCardNumber("");
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    @Test
    @DisplayName("Поле номер карты спецсимволы")
    void fieldNumberCardSpecialSymbol() {
        journeyPage.fillPaymentForm("!!!! .... №№№№ %%%%",
                validCardData.getMonth(),
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.checkCardNumber("");
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }
    // МЕСЯЦ

    @Test
    @DisplayName("Поле месяц пустое")
    void fieldMonthEmpty() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                "",
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    @Test
    @DisplayName("Поле месяц одна цифра")
    void fieldMonthOneNumber() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                "1",
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    @Test
    @DisplayName("Поле месяц три цифры")
    void fieldMonthThreeNumber() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                "333",
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Поле месяц несуществующий месяц более 12-ти")
    void fieldMonthMissingThirteen() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                "13",
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Поле месяц несуществующий месяц менее 01")
    void fieldMonthMissingZero() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                "00",
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Поле месяц несуществующий 00, год больше текущего")
    void fieldMonthMissingFuture() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                "00",
                DataHelper.generateYear(1),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Поле месяц +1 от текущего, год +5 от текущего, будущее")
    void fieldMonthFutureOneMonth() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                DataHelper.generateMonth(1),
                DataHelper.generateYear(5),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Поле месяц буквы")
    void fieldMonthLetters() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                "аа",
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.checkMonth("");
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    @Test
    @DisplayName("Поле месяц спецсимволы")
    void fieldMonthSpecialSymbol() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                "!-",
                validCardData.getYear(),
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.checkMonth("");
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    // ГОД
    @Test
    @DisplayName("Поле год пустое")
    void fieldYearEmpty() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                "",
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    @Test
    @DisplayName("Поле год одна цифра")
    void fieldYearOneNumber() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                "1",
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    @Test
    @DisplayName("Поле год три цифры")
    void fieldYearThreeNumber() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                "333",
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.checkYear("33");
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Поле год прошлое")
    void fieldYearLast() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                "24",
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Поле год будущее, более 5-ти лет")
    void fieldYearFuture() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                "31",
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Поле год буквы")
    void fieldYearLetters() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                "аа",
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.checkYear("");
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    @Test
    @DisplayName("Поле год спецсимволы")
    void fieldYearSpecialSymbol() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                "!-",
                validCardData.getOwner(),
                validCardData.getCvc());
        journeyPage.checkYear("");
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    // ВЛАДЕЛЕЦ
    @Test
    @DisplayName("Поле владелец пустое")
    void fieldOwnerEmpty() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                validCardData.getYear(),
                "",
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Поле владелец одна буква")
    void fieldOwnerOneLetter() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                validCardData.getYear(),
                "а",
                validCardData.getCvc());
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Введите корректное имя");
    }

    @Test
    @DisplayName("Поле владелец цифры")
    void fieldOwnerNumber() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                validCardData.getYear(),
                "12345",
                validCardData.getCvc());
        journeyPage.checkOwner("");
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Поле владелец спецсимволы")
    void fieldOwnerSpecialSymbol() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                validCardData.getYear(),
                "!,№%:",
                validCardData.getCvc());
        journeyPage.checkOwner("");
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Поле обязательно для заполнения");
    }

    //CVC/CVV
    @Test
    @DisplayName("Поле CVC/CVV пустое")
    void fieldCvcCvvEmpty() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                validCardData.getYear(),
                validCardData.getOwner(),
                "");
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    @Test
    @DisplayName("Поле CVC/CVV одна цифра")
    void fieldCvcCvvOneNumber() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                validCardData.getYear(),
                validCardData.getOwner(),
                "1");
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    @Test
    @DisplayName("Поле CVC/CVV четыре цифры")
    void fieldCvcCvvFourNumber() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                validCardData.getYear(),
                validCardData.getOwner(),
                "4444");
        journeyPage.checkCvc("444");
        journeyPage.continueButton();
        journeyPage.checkOkMessage("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("Поле CVC/CVV буквы")
    void fieldCvcCvvLetters() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                validCardData.getYear(),
                validCardData.getOwner(),
                "ааа");
        journeyPage.checkCvc("");
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }

    @Test
    @DisplayName("Поле CVC/CVV спецсимволы")
    void fieldCvcCvvSpecialSymbol() {
        journeyPage.fillPaymentForm(validCardData.getNumber(),
                validCardData.getMonth(),
                validCardData.getYear(),
                validCardData.getOwner(),
                "!-");
        journeyPage.checkCvc("");
        journeyPage.continueButton();
        journeyPage.checkErrorFields("Неверный формат");
    }
}