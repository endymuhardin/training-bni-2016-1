package com.brainmatics.pelatihan;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsViewResolver;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication
@EnableOAuth2Client
@EntityScan(
        basePackageClasses = {AplikasiPelatihanApplication.class, Jsr310JpaConverters.class}
)
public class AplikasiPelatihanApplication {

    public static void main(String[] args) {
        SpringApplication.run(AplikasiPelatihanApplication.class, args);
    }

    @Bean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }

    @Bean
    @Qualifier("onlineTestClient")
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
            OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }
    
    @Bean
    public JasperReportsViewResolver jasperResolver(){
        JasperReportsViewResolver resolver = new JasperReportsViewResolver();
        
        resolver.setViewClass(JasperReportsMultiFormatView.class);
        resolver.setOrder(0); // cari template jasper dulu, kalau tidak ketemu, baru cari template thymeleaf
        resolver.setViewNames("report/*");
        resolver.setPrefix("classpath:/");
        resolver.setSuffix(".jrxml");
        resolver.setReportDataKey("dataDalamReport");
        
        return resolver;
    }
}
