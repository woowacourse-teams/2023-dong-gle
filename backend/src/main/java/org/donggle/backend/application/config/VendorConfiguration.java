package org.donggle.backend.application.config;

import org.donggle.backend.application.BlogClient;
import org.donggle.backend.application.service.BlogClients;
import org.donggle.backend.application.service.vendor.notion.NotionApiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class VendorConfiguration {
    @Bean
    public BlogClients blogClients(final Set<BlogClient> clients) {
        return new BlogClients(clients);
    }

    @Bean
    public NotionApiService notionApiService() {
        return new NotionApiService();
    }
}
