package model;

public class CourierCreds {


    public String getLogin() { return login; }
    public String getPassword() {
        return password;
    }

    public CourierCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }

    private String login;
    private String password;

    public static CourierCreds getCredsFromCourier (Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword());
    }
}