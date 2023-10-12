package ua.chernonog.users.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "users")
public record UsersProps(
        int legalAge
) {
}

