package demo.contract.adapter.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import demo.contract.application.service.CreateContractService;
import demo.contract.application.service.CreateQuoteService;
import demo.contract.application.service.GetPolicyByIdService;
import demo.contract.application.service.ListPolicyService;

@Configuration
@ComponentScan(
    basePackages = "demo.contract.application.service",
    includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = {
            GetPolicyByIdService.class,
            ListPolicyService.class,
            CreateQuoteService.class,
            CreateContractService.class
        }
    ),
    useDefaultFilters = false
)
public class BeanInjectionConfiguration {
    
}
