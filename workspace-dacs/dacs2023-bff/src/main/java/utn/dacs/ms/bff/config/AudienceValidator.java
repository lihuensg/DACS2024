package utn.dacs.ms.bff.config;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public class AudienceValidator implements OAuth2TokenValidator<Jwt> {
    private final boolean active;
    private final List<String> auds;
    private final OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is missing", null);

    public AudienceValidator(boolean active, List<String> auds) {
        this.active = active;
        this.auds = auds;
    }

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if(!this.active) {
            return OAuth2TokenValidatorResult.success();
        } else if (jwt.getAudience() != null && jwt.getAudience().stream().anyMatch(this.auds::contains)) {
            return OAuth2TokenValidatorResult.success();
        } else {
            return OAuth2TokenValidatorResult.failure(this.error);
        }
    }
}
