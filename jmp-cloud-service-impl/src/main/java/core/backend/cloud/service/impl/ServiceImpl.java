package core.backend.cloud.service.impl;

import core.backend.dto.BankCard;
import core.backend.dto.Subscription;
import core.backend.dto.User;
import core.backend.service.api.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServiceImpl implements Service {

    private final Map<User, List<BankCard>> storage = new ConcurrentHashMap<>();
    private final List<Subscription> subscriptions = new ArrayList<>();
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    @Override
    public void subscribe(BankCard bankCard) {
        if (bankCard == null) {
            throw new IllegalArgumentException("User or his (her) birthday is null");
        }
        reentrantReadWriteLock.writeLock().lock();
        try {
            var user = bankCard.getUser();
            storage.computeIfAbsent(user, u -> new ArrayList<>()).add(bankCard);
            subscriptions.add(new Subscription(bankCard.getNumber(), LocalDate.now()));
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber) {
        return subscriptions.stream()
                .filter(subscription -> subscription.getBankcard().equals(bankCardNumber))
                .findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        return storage.keySet().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate) {
        return subscriptions.stream().filter(predicate).collect(Collectors.toUnmodifiableList());
    }
}
