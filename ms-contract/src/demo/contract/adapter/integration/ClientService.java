package demo.contract.adapter.integration;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import demo.contract.application.exception.InternalErrorException;
import demo.contract.application.exception.InvalidRequestException;
import demo.contract.application.exception.ResourceNotFoundException;
import demo.contract.application.gateway.ClientServiceGateway;

@Component
public class ClientService implements ClientServiceGateway {

     private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final String baseUrlClienteService;

    public ClientService(@Value("integration.client.base-url") String baseUrlClienteService) {
        this.baseUrlClienteService = baseUrlClienteService;
    }

    @Override
    public Optional<GetClienteByIdOutput> getById(String id) {
        try{
            return Optional.ofNullable(
                RestClient
                    .create(baseUrlClienteService)
                    .method(HttpMethod.GET)
                    .uri("/clients/{id}", id)
                    .retrieve()
                        .onStatus(s -> s.value() ==  BAD_REQUEST.value() , (req, res) -> {
                            throw new InvalidRequestException("client Id '%s' is invalid".formatted(id)); 
                        })
                        .onStatus(s -> s.value() == NOT_FOUND.value(), (req, res) -> {
                            throw new ResourceNotFoundException(); 
                        })
                        .onStatus( s -> s.isError(),  (req, res) -> {
                            var message = "Internal error when requesting client service";
                            var body = new String(res.getBody().readAllBytes(), StandardCharsets.UTF_8);
                            log.error("{} : response = {}", message, body);
                            throw new InternalErrorException(message);
                        })
                    .body(GetClienteByIdOutput.class)
            );
        }catch(ResourceNotFoundException ex){
            return Optional.empty();
        }
    }
    
}
