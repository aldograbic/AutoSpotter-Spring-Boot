package com.example.AutoSpotter.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;

@Configuration
public class GoogleCloudStorageConfig {
    
    @Bean
    public StorageOptions storageOptions() throws IOException {
        GoogleCredentials credentials = ServiceAccountCredentials.fromStream(getClass().getResourceAsStream("/peerless-tiger-394915-c4668df1d81d.json"));
        return StorageOptions.newBuilder().setCredentials(credentials).build();
    }

    @Bean
    public Storage storage(StorageOptions storageOptions) {
        return storageOptions.getService();
    }
}