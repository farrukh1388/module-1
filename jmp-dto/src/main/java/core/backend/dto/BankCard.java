package core.backend.dto;

public class BankCard {

    private final String number;
    private final User user;

    public BankCard(String number, User user) {
        this.number = number;
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public User getUser() {
        return user;
    }
}
