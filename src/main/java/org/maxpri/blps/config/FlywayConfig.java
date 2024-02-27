package org.maxpri.blps.config;

import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

/**
 * @author max_pri
 */
@Configuration
public class FlywayConfig {

    @Autowired @Qualifier("articleDataSource")
    private DataSource articleDataSource;

    @Autowired @Qualifier("imageDataSource")
    private DataSource imageDataSource;

    @PostConstruct
    @DependsOn({"imageDataSource", "articleDataSource"})
    public void migrate() {
        Flyway flywayArticle = Flyway.configure()
                .dataSource(articleDataSource)
                .locations("classpath:db/article_migration")
                .baselineOnMigrate(true)
                .load();
        Flyway flywayImage = Flyway.configure()
                .dataSource(imageDataSource)
                .locations("classpath:db/image_migration")
                .baselineOnMigrate(true)
                .load();
        flywayArticle.migrate();
        flywayImage.migrate();
    }
}
