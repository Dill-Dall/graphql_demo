package no.dilldall.graphql_demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .authorizeHttpRequests { authorize: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry ->
                authorize
                    .requestMatchers("/graphiql", "/graphql").authenticated()
                    .anyRequest().permitAll()
            }
            .httpBasic { httpBasicCustomizer -> httpBasicCustomizer }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}


@Configuration
class InMemoryUserDetailsService(
    private val passwordEncoder: PasswordEncoder,
    @Value("\${app.security.user.username}") private val userUsername: String,
    @Value("\${app.security.user.password}") private val userPassword: String,
    @Value("\${app.security.admin.username}") private val adminUsername: String,
    @Value("\${app.security.admin.password}") private val adminPassword: String
) {

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user = User.builder()
            .username(userUsername)
            .password(passwordEncoder.encode(userPassword))
            .roles("USER")
            .build()

        val admin = User.builder()
            .username(adminUsername)
            .password(passwordEncoder.encode(adminPassword))
            .roles("ADMIN")
            .build()

        return InMemoryUserDetailsManager(user, admin)
    }
}