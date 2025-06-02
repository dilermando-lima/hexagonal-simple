package demo.client.adapter.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import demo.client.application.service.CreateClientService;
import demo.client.application.service.DeleteClientByIdService;
import demo.client.application.service.GetClientByIdService;
import demo.client.application.service.ListClientService;
import demo.client.application.service.UpdateClientByIdService;

@Configuration
@ComponentScan(
    basePackages = "demo.client.application.service",
    includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = {
            CreateClientService.class,
            ListClientService.class,
            GetClientByIdService.class,
            UpdateClientByIdService.class,
            DeleteClientByIdService.class
        }
    ),
    useDefaultFilters = false
)
public class BeanInjectionConfiguration {
    
}
