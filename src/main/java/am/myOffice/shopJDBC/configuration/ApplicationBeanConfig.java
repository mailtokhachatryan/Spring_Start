package am.myOffice.shopJDBC.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
public class ApplicationBeanConfig {

    @Bean
    public SingleConnectionDataSource singleConnectionDataSource() {
        SingleConnectionDataSource singleConnectionDataSource = new SingleConnectionDataSource();
        singleConnectionDataSource.setUrl("jdbc:postgresql://localhost:5432/group1");
        singleConnectionDataSource.setUsername("postgres");
        singleConnectionDataSource.setPassword("postgres");
        singleConnectionDataSource.setDriverClassName("org.postgresql.Driver");
        return singleConnectionDataSource;
    }

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean(SingleConnectionDataSource singleConnectionDataSource) {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(singleConnectionDataSource);
        return localSessionFactoryBean;
    }
    
}
