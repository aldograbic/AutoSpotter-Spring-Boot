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
        // Load the Google Cloud Storage credentials from the JSON key file
        GoogleCredentials credentials = ServiceAccountCredentials.fromStream(getClass().getResourceAsStream("/peerless-tiger-394915-c4668df1d81d.json"));

        // Set up the credentials for the StorageOptions
        return StorageOptions.newBuilder().setCredentials(credentials).build();
    }

    @Bean
    public Storage storage(StorageOptions storageOptions) {
        // Create a Storage instance using the provided StorageOptions
        return storageOptions.getService();
    }
}

