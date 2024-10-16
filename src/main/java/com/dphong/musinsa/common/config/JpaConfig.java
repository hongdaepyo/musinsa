package com.dphong.musinsa.common.config;

import com.dphong.musinsa.MusinsaApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = MusinsaApplication.BASE_PACKAGE)
@EnableJpaRepositories(basePackages = MusinsaApplication.BASE_PACKAGE)
public class JpaConfig {

}
