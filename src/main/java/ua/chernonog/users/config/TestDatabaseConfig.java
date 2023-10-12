package ua.chernonog.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class TestDatabaseConfig {
    @Bean
    public DataSource dataSource() {
        // Настройка и создание тестовой базы данных, например, в памяти H2
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql") // Создание схемы БД
                .addScript("classpath:data.sql")   // Вставка тестовых данных
                .build();
    }
}