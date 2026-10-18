package io.r4v3chain.backend.ops;

import io.r4v3chain.backend.config.ProductProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Phase T/Z — expose persistance et mode produit via /actuator/info.
 */
@Component
public class PersistenceModeInfoContributor implements InfoContributor {

    @Value("${r4v3chain.persistence.mode:memory}")
    private String persistenceMode;

    private final ProductProperties productProperties;

    public PersistenceModeInfoContributor(ProductProperties productProperties) {
        this.productProperties = productProperties;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail(
                "r4v3chain",
                Map.of(
                        "persistence-mode", persistenceMode,
                        "postgres-only-profiles", "prod,staging",
                        "commercial", productProperties.isCommercial(),
                        "faucet-enabled", productProperties.isFaucetEnabled(),
                        "showcase-enabled", productProperties.isShowcaseEnabled()
                )
        );
    }
}
