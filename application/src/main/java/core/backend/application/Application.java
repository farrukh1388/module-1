package core.backend.application;

import core.backend.application.exception.SubscriptionNotFoundException;
import core.backend.bank.api.Bank;
import core.backend.dto.BankCard;
import core.backend.dto.BankCardType;
import core.backend.dto.User;
import core.backend.service.api.Service;

import java.time.LocalDate;
import java.util.ServiceLoader;

public class Application {

    public static void main(String[] args) {
        Bank bank = ServiceLoader.load(Bank.class).findFirst()
                .orElseThrow(() -> new IllegalStateException("Can't find Bank class implementation"));
        Service service = ServiceLoader.load(Service.class).findFirst()
                .orElseThrow(() -> new IllegalStateException("Can't find Service class implementation"));
        User user = initializeUser();

        BankCard userCreditCard = bank.createBankCard(user, BankCardType.CREDIT);
        BankCard userDebitCard = bank.createBankCard(user, BankCardType.DEBIT);

        System.out.println("\nGetting message that subscription not found");
        String errorMessage = String.format("Subscription with number %s not found", userCreditCard.getNumber());
        try {
            service.getSubscriptionByBankCardNumber(userCreditCard.getNumber())
                    .orElseThrow(() -> new SubscriptionNotFoundException(errorMessage));
        } catch (SubscriptionNotFoundException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nMaking subscription");
        service.subscribe(userCreditCard);
        service.subscribe(userDebitCard);

        System.out.println("\nGetting subscriptions: ");
        service.getSubscriptionByBankCardNumber(userCreditCard.getNumber()).ifPresent(System.out::println);
        service.getSubscriptionByBankCardNumber(userDebitCard.getNumber()).ifPresent(System.out::println);

        System.out.println("\nAll users: " + service.getAllUsers());
        System.out.println("\nUsers average age: " + service.getAverageUsersAge());
        System.out.println("\nUser is payable: " + Service.isPayableUser(user));
        System.out.println("\nGetting all subscriptions for today: " + service.getAllSubscriptionsByCondition(s -> s.getStartDate().isEqual(LocalDate.now())));
    }

    private static User initializeUser() {
        User user = new User("name", "surname", LocalDate.of(1960, 6, 20));

        System.out.println("\nUser: " + user);
        return user;
    }
}
