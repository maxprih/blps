package org.maxpri.blps.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

/**
 * @author max_pri
 */
@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "articleDataSourceConfiguration",
        transactionManagerRef = "myTransactionManager",
        basePackages = {"org.maxpri.blps.repository.articleRepository"}
)
public class ArticleDataSourceConfig {

    private final JpaConfig jpaConfig;

    @Autowired
    public ArticleDataSourceConfig(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    @Bean(name = "articleEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder articleEntityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(), jpaConfig.getProperties(), null
        );
    }


    @Bean(name = "articleDataSourceConfiguration")
    public LocalContainerEntityManagerFactoryBean articleEntityManager(
            @Qualifier("articleEntityManagerFactoryBuilder") EntityManagerFactoryBuilder articleEntityManagerFactoryBuilder,
            @Qualifier("articleDataSource") DataSource postgresDataSource
    ) {
        return articleEntityManagerFactoryBuilder
                .dataSource(postgresDataSource)
                .packages("org.maxpri.blps.model.entity.articleEntity")
                .persistenceUnit("postgres")
                .properties(jpaConfig.getProperties())
                .jta(true)
                .build();
    }

    @Bean("articleDataSourceProperties")
    @ConfigurationProperties("datasource.article")
    public DataSourceProperties articleDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean("articleDataSource")
    @ConfigurationProperties("datasource.article")
    public DataSource articleDataSource(@Qualifier("articleDataSourceProperties") DataSourceProperties articleDataSource) {
        PGXADataSource ds = new PGXADataSource();
        ds.setUrl(articleDataSource.getUrl());
        ds.setUser(articleDataSource.getUsername());
        ds.setPassword(articleDataSource.getPassword());

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(ds);
        xaDataSource.setMaxPoolSize(5);
        xaDataSource.setUniqueResourceName("xa_article");
        return xaDataSource;
    }
}
