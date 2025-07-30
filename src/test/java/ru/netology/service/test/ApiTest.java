package ru.netology.service.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.netology.service.data.DataHelper;
import ru.netology.service.db.DBUtils;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.service.db.ApiData.buyTour;

public class ApiTest {
    @SneakyThrows
    @Test
        // Покупка с валидной карты
    void buyStatusApprovedWithValidCard() {
        var validCard = DataHelper.getValidCardData(); //данные
        var response = buyTour(validCard); // покупка

        response.then() // Проверяем статус ответа и статус операции
                .statusCode(200)
                .body("status", equalTo("APPROVED"));
        while
        (!DBUtils.getValidVerificationStatusApproved().equals("APPROVED")) {
            Thread.sleep(1000);
        }
        String statusInDb = DBUtils.getValidVerificationStatusApproved(); // Проверяем статус в БД
        assertEquals("APPROVED", statusInDb);
    }

    @SneakyThrows
    @Test
        // Покупка с заблокированной карты
    void buyStatusDeclinedWithWithInvalidCard() {
        var invalidCard = DataHelper.getBlockedCardData(); // данные
        var response = buyTour(invalidCard); //покупка

        response.then() // Проверяем статус ответа и статус операции
                .statusCode(200)
                .body("status", equalTo("DECLINED"));

        while
        (!DBUtils.getValidVerificationStatusDeclined().equals("DECLINED")) {
            Thread.sleep(1000);
        }

        String statusInDb = DBUtils.getValidVerificationStatusDeclined(); // Проверяем статус в БД
        assertEquals("DECLINED", statusInDb);
    }
}
