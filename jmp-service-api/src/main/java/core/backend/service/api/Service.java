package core.backend.service.api;

import core.backend.dto.BankCard;
import core.backend.dto.Subscription;
import core.backend.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber);

    List<User> getAllUsers();

    default double getAverageUsersAge() {
        var users = getAllUsers();
        return users.stream()
                .mapToLong(user -> ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()))
                .average().orElse(0);
    }

    static boolean isPayableUser(User user) {
        if (user == null || user.getBirthday() == null) {
            throw new IllegalArgumentException("User or his (her) birthday is null");
        }
        return ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()) >= 18;
    }

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate);
}
