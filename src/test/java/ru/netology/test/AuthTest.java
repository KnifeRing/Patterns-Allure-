package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.selenide.AllureSelenide;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.data.DataGenerator.Registration.getUser;
import static ru.netology.data.DataGenerator.getRandomLogin;
import static ru.netology.data.DataGenerator.getRandomPassword;

public class AuthTest {

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
            open("http://localhost:9999");
        }

        @Test
        @DisplayName("Should successfully login with active registered user")
        void shouldSuccessfulLoginIfRegisteredActiveUser() {
            var registeredUser = getRegisteredUser("active");
            // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
            //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
            //  пользователя registeredUser
            $("[data-test-id=login] .input__control").setValue(registeredUser.getLogin());
            $("[data-test-id=password] .input__control").setValue(registeredUser.getPassword());
            $(".button").click();
            $(".heading").shouldBe(visible).shouldHave(exactText("Личный кабинет"));
        }

        @Test
        @DisplayName("Should get error message if login with not registered user")
        void shouldGetErrorIfNotRegisteredUser() {
            var notRegisteredUser = getUser("active");
            // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
            //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
            $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
            $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
            $(".button").click();
            $(".notification .notification__content").shouldBe(visible).shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
        }

        @Test
        @DisplayName("Should get error message if login with blocked registered user")
        void shouldGetErrorIfBlockedUser() {
            var blockedUser = getRegisteredUser("blocked");
            // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
            //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
            $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
            $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
            $(".button").click();
            $(".notification .notification__content").shouldBe(visible).shouldHave(exactText("Ошибка! Пользователь заблокирован"));
        }

        @Test
        @DisplayName("Should get error message if login with wrong login")
        void shouldGetErrorIfWrongLogin() {
            var registeredUser = getRegisteredUser("active");
            var wrongLogin = getRandomLogin();
            // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
            //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
            //  "Пароль" - пользователя registeredUser
            $("[data-test-id='login'] input").setValue(wrongLogin);
            $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
            $(".button").click();
            $(".notification .notification__content").shouldBe(visible).shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
        }

        @Test
        @DisplayName("Should get error message if login with wrong password")
        void shouldGetErrorIfWrongPassword() {
            var registeredUser = getRegisteredUser("active");
            var wrongPassword = getRandomPassword();
            // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
            //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
            //  "Пароль" - переменную wrongPassword
            $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
            $("[data-test-id='password'] input").setValue(wrongPassword);
            $(".button").click();
            $(".notification .notification__content").shouldBe(visible).shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
        }
        @Test
        @DisplayName("Should get error message if login with not registered and blocked user")
        void shouldGetErrorIfNotRegisteredBlockedUser() {
            var notRegisteredUser = getUser("blocked");
            $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
            $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
            $(".button").click();
            $(".notification .notification__content").shouldBe(visible).shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
        }

        @Test
        @DisplayName("Should get error message if login with wrong password and user blocked")
        void shouldGetErrorIfWrongPasswordAndUserBlocked() {
            var registeredUser = getRegisteredUser("blocked");
            var wrongPassword = getRandomPassword();
            $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
            $("[data-test-id='password'] input").setValue(wrongPassword);
            $(".button").click();
            $(".notification .notification__content").shouldBe(visible).shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
        }

        @Test
        @DisplayName("Should get error message if login with wrong login and blocked user")
        void shouldGetErrorIfWrongLoginAndBlockedUser() {
            var registeredUser = getRegisteredUser("blocked");
            var wrongLogin = getRandomLogin();
            $("[data-test-id='login'] input").setValue(wrongLogin);
            $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
            $(".button").click();
            $(".notification .notification__content").shouldBe(visible).shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
        }
    }