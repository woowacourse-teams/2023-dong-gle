package org.donggle.backend.application.config;

import org.donggle.backend.application.service.vendor.medium.MediumApiService;
import org.donggle.backend.application.service.vendor.notion.NotionApiService;
import org.donggle.backend.application.service.vendor.tistory.TistoryApiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VendorConfiguration {
    @Bean
    public TistoryApiService tistoryApiService() {
        return new TistoryApiService();
    }

    @Bean
    public MediumApiService mediumApiService() {
        return new MediumApiService();
    }

    @Bean
    public NotionApiService notionApiService() {
        return new NotionApiService();
    }
}
