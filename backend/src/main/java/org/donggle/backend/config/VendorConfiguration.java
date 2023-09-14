package org.donggle.backend.config;

import org.donggle.backend.application.client.BlogClient;
import org.donggle.backend.application.client.LoginClient;
import org.donggle.backend.application.service.auth.LoginClients;
import org.donggle.backend.domain.blog.BlogClients;
import org.donggle.backend.infrastructure.client.notion.NotionApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class VendorConfiguration {
    @Bean
    public LoginClients loginClients(final Set<LoginClient> clients) {
        return new LoginClients(clients);
    }

    @Bean
    public BlogClients blogClients(final Set<BlogClient> clients) {
        return new BlogClients(clients);
    }

    @Bean
    public NotionApiClient notionApiService() {
        return new NotionApiClient();
    }
}
