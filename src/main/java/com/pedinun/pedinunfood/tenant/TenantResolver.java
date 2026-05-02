package com.pedinun.pedinunfood.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantResolver implements CurrentTenantIdentifierResolver<Long> { // Use Long aqui

    @Override
    public Long resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getCurrentTenant();

        // Converte a String do contexto para Long antes de entregar ao Hibernate
        if (tenantId != null && !tenantId.equals("0")) {
            return Long.valueOf(tenantId);
        }

        return 0L;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
