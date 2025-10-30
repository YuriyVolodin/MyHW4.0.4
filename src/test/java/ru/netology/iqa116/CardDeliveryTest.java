package ru.netology.iqa116;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    public String generateDate(int days, String pattern) {
        return LocalDate.now()
                .plusDays(days)
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setup() {
        Configuration.browserSize = "1920x1080";
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitFormSuccessfully() {
        String date = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Юрий Володин");
        $("[data-test-id=phone] input").setValue("+79012345678");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(
                        Condition.text("Успешно!"),
                        Condition.text("Встреча успешно забронирована на " + date)
                );
    }
}