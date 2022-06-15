package core.backend.bank.api;

import core.backend.dto.BankCard;
import core.backend.dto.BankCardType;
import core.backend.dto.User;

public interface Bank {

    BankCard createBankCard(User user, BankCardType bankCardType);
}
