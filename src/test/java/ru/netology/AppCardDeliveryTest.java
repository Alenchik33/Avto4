package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {

    public String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    public void shouldSendFormSuccessfulTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Кемерово");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Демидов Вадим Яковлевич");
        $("[data-test-id='phone'] input").setValue("+79284567891");
        $("[data-test-id='agreement']").click();
        $(".button.button").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + currentDate), Duration.ofSeconds(15)).shouldBe(visible);
    }
    @Test
    public void shouldSendFormAdd7DaysTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Кемерово");
        String currentDate = generateDate(7, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Демидов Вадим Яковлевич");
        $("[data-test-id='phone'] input").setValue("+79284567891");
        $("[data-test-id='agreement']").click();
        $(".button.button").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + currentDate), Duration.ofSeconds(15)).shouldBe(visible);
    }
    @Test
    public void shouldReturnErrorAdd1DayNegativeTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Кемерово");
        String currentDate = generateDate(1, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Демидов Вадим Яковлевич");
        $("[data-test-id='phone'] input").setValue("+79284567891");
        $("[data-test-id='agreement']").click();
        $(".button.button").click();
        $$(".input__sub").find(Condition.text("Заказ на выбранную дату не возможен"));
    }
    @Test
    public void shouldReturnErrorIncorrectPhoneNegativeTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Кемерово");
        String currentDate = generateDate(1, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Демидов Вадим Яковлевич");
        $("[data-test-id='phone'] input").setValue("+7928456789");
        $("[data-test-id='agreement']").click();
        $(".button.button").click();
        $$(".input__inner").find(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}

