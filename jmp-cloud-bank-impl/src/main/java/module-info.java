module jmp.cloud.bank.impl {

    requires jmp.dto;
    requires transitive jmp.bank.api;

    exports core.backend.cloud.bank.impl;

    provides core.backend.bank.api.Bank with core.backend.cloud.bank.impl.BankImpl;
}
