package utn.dacs.ms.bff.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Value("${spring.security.oauth2.resourceserver.jwk.key-set-uri}")
	private String publicKeyUri;

	@Value("${spring.security.oauth2.aud.active}")
	private boolean audEnabled;

	@Value("#{'${spring.security.oauth2.aud.values}'.split(',')}")
	private List<String> auds;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				.authorizeRequests(authorize -> authorize
						.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
						.antMatchers("/metrics", "/metrics/**").permitAll()
						//.antMatchers("/ping","/conector/ping","/backend/ping").permitAll()
						.antMatchers("/ping","/conector/ping").permitAll()
						//.antMatchers("/version","/conector/version","/backend/version").permitAll()
						.anyRequest().authenticated()
				).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.oauth2ResourceServer(server -> server.jwt(jwt -> jwt
						.and().authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())));

		return http.build();
	}

	/**
	 * Usar como ejemplo en caso de necesitar comprobar nuestro propio mecanismo
	 * @return
	 */
	@Bean
    protected JwtDecoder jwtDecoder() {
		OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(this.audEnabled, this.auds);
		OAuth2TokenValidator<Jwt> withStandards = JwtValidators.createDefault();
        OAuth2TokenValidator<Jwt> withBoth = new DelegatingOAuth2TokenValidator<>(withStandards, audienceValidator);
		NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(this.publicKeyUri).build();
        decoder.setJwtValidator(withBoth);
		return decoder;
    }
}
