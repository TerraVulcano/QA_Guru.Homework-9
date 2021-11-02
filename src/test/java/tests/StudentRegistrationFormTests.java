package tests;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class StudentRegistrationFormTests extends TestBase{

    @Test
    void successfulFillFormTest() {
        Faker faker = new Faker();

        String firstName = faker.name().firstName(),
                lastName = faker.name().lastName(),
                userEmail = faker.internet().emailAddress(),
                gender = faker.demographic().sex(),
                userNumber = faker.number().digits(10),
                currentAddress = faker.address().fullAddress(),
                myDay = "14",
                myMonth = "September",
                myYear = "1990",
                subject = "Chemistry",
                hobby = "Music",
                picture = "vulcano.jpg",
                state = "Rajasthan",
                city = "Jaipur";

        step("Open students registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Fill students registration form", () -> {
            step("Fill common data", () -> {
                $("#firstName").setValue(firstName);
                $("#lastName").setValue(lastName);
                $("#userEmail").setValue(userEmail);
                $(byText(gender)).doubleClick();
                $("#userNumber").setValue(userNumber);
            });

            step("Set date", () -> {
                $("#dateOfBirthInput").clear();
                $(".react-datepicker__month-select").selectOption(myMonth);
                $(".react-datepicker__year-select").selectOption(myYear);
                $(".react-datepicker__day--0" + myDay).click();
            });

            step("Set subjects and hobbies", () -> {
                $("#subjectsInput").setValue(subject).pressEnter();
                $(byText(hobby)).click();
            });

            step("Upload image", () ->
                    $("#uploadPicture").uploadFromClasspath(picture));

            step("Set address", () -> {
                $("#currentAddress").setValue(currentAddress);
                $("#react-select-3-input").scrollTo().setValue(state).pressEnter();
                $("#react-select-4-input").setValue(city).pressEnter();
            });

            step("Submit form", () ->
                    $("#submit").click());
        });

        step("Verify successful form submit", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $x("//td[text()='Student Name']").parent().shouldHave(text(firstName + " " + lastName));
            $x("//td[text()='Student Email']").parent().shouldHave(text(userEmail));
            $x("//td[text()='Gender']").parent().shouldHave(text(gender));
            $x("//td[text()='Mobile']").parent().shouldHave(text(userNumber));
            $x("//td[text()='Date of Birth']").parent().shouldHave(text(myDay + " " + myMonth + "," + myYear));
            $x("//td[text()='Subjects']").parent().shouldHave(text(subject));
            $x("//td[text()='Hobbies']").parent().shouldHave(text(hobby));
            $x("//td[text()='Picture']").parent().shouldHave(text(picture));
            $x("//td[text()='Address']").parent().shouldHave(text(currentAddress));
            $x("//td[text()='State and City']").parent().shouldHave(text(state + " " + city));
        });
    }
}
