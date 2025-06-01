package demo.customer.adapter.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import demo.customer.application.service.CreateClientService;
import demo.customer.application.service.DeleteClientByIdService;
import demo.customer.application.service.GetClientByIdService;
import demo.customer.application.service.ListClientService;
import demo.customer.application.service.UpdateClientByIdService;

@Configuration
@ComponentScan(
    basePackages = "demo.customer.application.service",
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
