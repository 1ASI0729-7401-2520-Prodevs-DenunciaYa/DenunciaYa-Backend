package com.denunciayabackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);

    @Value("${uploads.dir:uploads}")
    private String uploadsDirProperty;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadsDir = Paths.get(uploadsDirProperty);
        String uploadsPath = uploadsDir.toFile().getAbsolutePath();
        // Serve files under /uploads/** from the filesystem uploads directory
        LOGGER.info("Mapping /uploads/** to file:{}{} (uploads.dir={})", uploadsPath, File.separator, uploadsDirProperty);
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadsPath + "/");
    }
}
