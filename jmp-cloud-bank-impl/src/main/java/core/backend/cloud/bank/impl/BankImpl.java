package core.backend.cloud.bank.impl;

import core.backend.bank.api.Bank;
import core.backend.dto.BankCard;
import core.backend.dto.BankCardType;
import core.backend.dto.CreditBankCard;
import core.backend.dto.DebitBankCard;
import core.backend.dto.User;

import java.util.UUID;
import java.util.function.BiFunction;

public class BankImpl implements Bank {

    @Override
    public BankCard createBankCard(User user, BankCardType bankCardType) {
        BiFunction<String, User, BankCard> function;
        if (BankCardType.CREDIT.equals(bankCardType)) {
            function = CreditBankCard::new;
        } else if (BankCardType.DEBIT.equals(bankCardType)) {
            function = DebitBankCard::new;
        } else {
            throw new IllegalArgumentException("Illegal bank card type");
        }
        return function.apply(UUID.randomUUID().toString(), user);
    }
}
