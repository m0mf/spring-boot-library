package org.example.config;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.controller.WebController;
import org.example.service.WebService;
import org.example.service.WebServiceRepo;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableConfigurationProperties({WebServiceProperties.class, MybatisProperties.class})
@ConditionalOnProperty(name = "library.enable", havingValue = "true")
@AutoConfiguration
public class LibraryConfig {


    @Configuration
    public static class CustomWebServiceConfiguration {
        private void setMapperLocations(MybatisProperties mybatisProperties, WebServiceProperties webServiceProperties) {
            String[] locations = mybatisProperties.getMapperLocations();
            String[] addLocations = new String[locations.length + 1];
            System.arraycopy(locations, 0, addLocations, 0, locations.length);
            addLocations[locations.length] = "file:" + webServiceProperties.getMapperLocations();
            mybatisProperties.setMapperLocations(addLocations);
        }

        @ConditionalOnMissingBean(WebService.class)
        @Bean
        public WebService webService(WebServiceProperties webServiceProperties, WebServiceRepo webServiceRepo) {
            WebService webService = new WebService(webServiceRepo);
            webService.setWebServiceProperties(webServiceProperties);
            return webService;
        }

        @ConditionalOnMissingBean(WebController.class)
        @Bean
        public WebController webController(WebService webService) {
            return new WebController(webService);
        }

        @Bean
        public WebServiceRepo webServiceRepo(SqlSession sqlSession, SqlSessionFactory sqlSessionFactory, MybatisProperties mybatisProperties, WebServiceProperties webServiceProperties) {
            setMapperLocations(mybatisProperties, webServiceProperties);
            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            MapperRegistry mapperRegistry = configuration.getMapperRegistry();

            return mapperRegistry.getMapper(WebServiceRepo.class, sqlSession);
        }
    }
}
