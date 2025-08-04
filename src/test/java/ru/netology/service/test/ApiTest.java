package ru.netology.service.test;

import org.junit.jupiter.api.Test;
import ru.netology.service.data.DataHelper;
import ru.netology.service.db.DBUtils;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.service.db.ApiData.buyTourCredit;
import static ru.netology.service.db.ApiData.buyTourPay;

public class ApiTest {

    @Test
    void buyStatusApprovedWithValidCardPay() { // Покупка с валидной карты
        var validCard = DataHelper.getValidCardData(); //данные
        var response = buyTourPay(validCard); // покупка

        response.then() // Проверяем статус ответа и статус операции
                .statusCode(200)
                .body("status", equalTo("APPROVED"));

        String statusInBd = DBUtils.getValidVerificationStatusPay(); // Проверяем статус в БД
        assertEquals("APPROVED", statusInBd);
    }

    @Test
    void buyStatusDeclinedWithInvalidCardPay() { // Покупка с заблокированной карты
        var invalidCard = DataHelper.getBlockedCardData();  //данные
        var response = buyTourPay(invalidCard);  // покупка

        response.then() // Проверяем статус ответа и статус операции
                .statusCode(200)
                .body("status", equalTo("DECLINED"));

        String statusInBd = DBUtils.getValidVerificationStatusPay(); // Проверяем статус в БД
        assertEquals("DECLINED", statusInBd);
    }

    @Test
    void buyStatusApprovedWithValidCardCredit() { // Покупка в кредит с валидной карты
        var validCard = DataHelper.getValidCardData(); //данные
        var response = buyTourCredit(validCard); // покупка

        response.then() // Проверяем статус ответа и статус операции
                .statusCode(200)
                .body("status", equalTo("APPROVED"));

        String statusInBd = DBUtils.getValidVerificationStatusCredit
                (); // Проверяем статус в БД
        assertEquals("APPROVED", statusInBd);
    }

    @Test
    void buyStatusDeclinedWithInvalidCardCredit() { // Покупка в кредит с заблокированной карты
        var invalidCard = DataHelper.getBlockedCardData();  //данные
        var response = buyTourCredit(invalidCard);  // покупка

        response.then() // Проверяем статус ответа и статус операции
                .statusCode(200)
                .body("status", equalTo("DECLINED"));

        String statusInBd = DBUtils.getValidVerificationStatusCredit(); // Проверяем статус в БД
        assertEquals("DECLINED", statusInBd);
    }
}
