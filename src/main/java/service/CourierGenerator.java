package service;

import model.Courier;
import utils.RandomGenerator;

public class CourierGenerator {

    public static Courier randomCourier() {
        return new Courier().withLogin(RandomGenerator.randomString(8))
                .withPassword(RandomGenerator.randomString(9))
                .withFirstName(RandomGenerator.randomString(8));
    }

    public static Courier testCourier() {
        return new Courier()
                .withLogin("test")
                .withPassword("test")
                .withFirstName("test");
    }

    public static Courier withNullPassword() {
        return new Courier()
                .withLogin(RandomGenerator.randomString(3))
                .withPassword(RandomGenerator.randomString(0))
                .withFirstName(RandomGenerator.randomString(2));
    }

    public static Courier withNullLogin() {
        return new Courier()
                .withLogin(RandomGenerator.randomString(0))
                .withPassword(RandomGenerator.randomString(5))
                .withFirstName(RandomGenerator.randomString(2));
    }
}