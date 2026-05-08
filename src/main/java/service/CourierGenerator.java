package service;

import io.qameta.allure.Step;
import model.Courier;
import utils.RandomGenerator;

public class CourierGenerator {

    @Step("Создание курьера со случайными параметрами")
    public static Courier randomCourier() {
        return new Courier().withLogin(RandomGenerator.randomString(8))
                .withPassword(RandomGenerator.randomString(9))
                .withFirstName(RandomGenerator.randomString(8));
    }

    @Step("Создание курьера с определенными параметрами")
    public static Courier testCourier() {
        return new Courier()
                .withLogin("Persefoniy_Scooter_God")
                .withPassword("password123")
                .withFirstName("Персефоний");
    }

    @Step("Создание курьера без пароля")
    public static Courier withNullPassword() {
        return new Courier()
                .withLogin(RandomGenerator.randomString(3))
                .withPassword(RandomGenerator.randomString(0))
                .withFirstName(RandomGenerator.randomString(2));
    }

    @Step("Создание курьера без логина")
    public static Courier withNullLogin() {
        return new Courier()
                .withLogin(RandomGenerator.randomString(0))
                .withPassword(RandomGenerator.randomString(5))
                .withFirstName(RandomGenerator.randomString(2));
    }
}