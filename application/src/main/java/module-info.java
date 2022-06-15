module application {

    requires jmp.dto;
    requires jmp.cloud.bank.impl;
    requires jmp.cloud.service.impl;

    uses core.backend.service.api.Service;
    uses core.backend.bank.api.Bank;
}
