package ru.netology.service.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private static Faker faker = new Faker(new Locale("ru")); // генерация русских данных

    private DataHelper() {
    }

    public static String getApprovedCardNumber() {// номер одобренной карты
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() { // номер заблокированной карты
        return "4444 4444 4444 4442";
    }

    public static String generateMonth(int monthChange) { //генерация месяца
        return LocalDate.now().plusMonths(monthChange)
                .format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateYear(int yearChange) { // генерация года
        return LocalDate.now().plusYears(yearChange)
                .format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateValidOwner() { //генерация валидного владельца
        return faker.name().fullName();
    }

    public static String generateValidCVC() { //генерация валидного CVV/CVC
        return faker.number().digits(3);
    }

    public static CardData getValidCardData() { //объект с одобренной картой
        return new CardData(
                getApprovedCardNumber(),
                generateMonth(0),
                generateYear(0),
                generateValidOwner(),
                generateValidCVC()
        );
    }

    public static CardData getBlockedCardData() { //объект с заблокированной картой
        return new CardData(
                getDeclinedCardNumber(),
                generateMonth(0),
                generateYear(0),
                generateValidOwner(),
                generateValidCVC()

        );
    }

    @Value
    public static class CardData {
        String number;
        String month;
        String year;
        String holder;
        String cvc;
    }
}
