module jmp.cloud.service.impl {

    requires jmp.dto;
    requires transitive jmp.service.api;

    exports core.backend.cloud.service.impl;

    provides core.backend.service.api.Service with core.backend.cloud.service.impl.ServiceImpl;
}
