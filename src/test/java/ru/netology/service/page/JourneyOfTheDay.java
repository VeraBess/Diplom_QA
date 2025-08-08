package ru.netology.service.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class JourneyOfTheDay {

    // Элементы для навигации по главной страницы
    public SelenideElement travelTitle = $(".heading_size_l"); //заголовок "Путешествие дня" на главной странице
    public SelenideElement buyButton = $$("button").findBy(text("Купить")); //кнопка купить
    public SelenideElement buyCreditButton = $$("button").findBy(text("Купить в кредит")); //кнопка купить в кредит
    public SelenideElement paymentByCard = $$("h3").findBy(text("Оплата по карте")); //надпись
    public SelenideElement creditToCardData = $$("h3").findBy(text("Кредит по данным карты")); //надпись

    // Элементы для проверки формы оплаты
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']"); //поле номер карты
    private SelenideElement monthField = $("[placeholder='08']"); //поле месяц
    private SelenideElement yearField = $("[placeholder='22']"); //поле год
    private SelenideElement ownerField = $$(".input__control").get(3); //поле владелец, четвертое поле в коллекции
    private SelenideElement cvcField = $("[placeholder='999']"); //поле CVC/CVV
    private SelenideElement payButton = $$("button").findBy(text("Продолжить")); //кнопка продолжить
    private ElementsCollection errorFields = $$(".input__sub"); //коллекция сообщений об ошибках под полями ввода
    private SelenideElement okMessage = $(".notification_status_ok .notification__content"); //сообщение об успешной операции
    private SelenideElement errorMessage = $(".notification_status_error .notification__content"); // сообщение об ошибке операции

    // Навигация
    public void openHeadPage() { // проверка загрузки главной страницы
        open("http://localhost:8080");
        travelTitle.shouldBe(visible);
    }

    public void clickBuyButton() { // клик по кнопке "Купить"
        buyButton.click();
    }

    public void clickBuyCreditButton() { // клик по кнопке "Купить в кредит"
        buyCreditButton.click();
    }

    // Форма оплаты
    public void fillPaymentForm(String cardNumber, String month, String year, String owner, String cvc) { //заполнение формы
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        cvcField.setValue(cvc);
    }

    public void continueButton() { // нажать кнопку "Продолжить"
        payButton.click();
    }

    public void checkOkMessage(String expectedText) { //сообщение об успехе, всплывающее окно
        okMessage.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text(expectedText));
    }

    public void checkErrorFields(String expectedText) { //подпись об ошибке под полем
        errorFields.findBy(text(expectedText))
                .shouldBe(visible);
    }

    public void checkErrorMessage(String expectedText) { // сообщение об ошибке, всплывающее окно
        errorMessage.shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text(expectedText));
    }

    // Валидация полей формы
    public void checkCardNumber(String value) {
        cardNumberField.shouldHave(value(value));
    }

    public void checkMonth(String value) {
        monthField.shouldHave(value(value));
    }

    public void checkYear(String value) {
        yearField.shouldHave(value(value));
    }

    public void checkOwner(String value) {
        ownerField.shouldHave(value(value));
    }

    public void checkCvc(String value) {
        cvcField.shouldHave(value(value));
    }
}

