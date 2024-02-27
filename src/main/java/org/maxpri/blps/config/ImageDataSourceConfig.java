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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

/**
 * @author max_pri
 */
@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "imageDataSourceConfiguration",
        transactionManagerRef = "myTransactionManager",
        basePackages = {"org.maxpri.blps.repository.imageRepository"}
)
public class ImageDataSourceConfig {

    private final JpaConfig jpaProperties;

    @Autowired
    public ImageDataSourceConfig(JpaConfig jpaProperties) {
        this.jpaProperties = jpaProperties;
    }

    @Bean(name = "imageEntityManagerFactoryBuilder")
    @Primary
    public EntityManagerFactoryBuilder imageEntityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(), jpaProperties.getProperties(), null
        );
    }


    @Bean(name = "imageDataSourceConfiguration")
    @Primary
    public LocalContainerEntityManagerFactoryBean imageEntityManager(
            @Qualifier("imageEntityManagerFactoryBuilder") EntityManagerFactoryBuilder imageManagerFactoryBuilder,
            @Qualifier("imageDataSource") DataSource postgresDataSource
    ) {
        return imageManagerFactoryBuilder
                .dataSource(postgresDataSource)
                .packages("org.maxpri.blps.model.entity.imageEntity")
                .persistenceUnit("postgres")
                .properties(jpaProperties.getProperties())
                .jta(true)
                .build();
    }

    @Bean("imageDataSourceProperties")
    @Primary
    @ConfigurationProperties("datasource.art-image")
    public DataSourceProperties imageDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean("imageDataSource")
    @Primary
    @ConfigurationProperties("datasource.art-image")
    public DataSource imageDataSource(@Qualifier("imageDataSourceProperties") DataSourceProperties imageDataSourceProperties) {
        PGXADataSource ds = new PGXADataSource();
        ds.setUrl(imageDataSourceProperties.getUrl());
        ds.setUser(imageDataSourceProperties.getUsername());
        ds.setPassword(imageDataSourceProperties.getPassword());

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(ds);
        xaDataSource.setMaxPoolSize(5);
        xaDataSource.setUniqueResourceName("xa_image");
        return xaDataSource;
    }
}
