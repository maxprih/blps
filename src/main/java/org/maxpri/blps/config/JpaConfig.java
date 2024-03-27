package org.maxpri.blps.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author max_pri
 */
@Configuration
@EnableJpaRepositories(basePackages = "org.maxpri.blps.repository.articleRepository")
public class JpaConfig {
}
