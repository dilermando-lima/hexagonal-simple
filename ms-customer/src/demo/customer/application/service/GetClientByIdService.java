package demo.customer.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import demo.customer.application.exception.ResourceNotFoundException;
import demo.customer.application.gateway.ClienteRepositoryGateway;
import demo.customer.application.model.ClientModel;



public class GetClientByIdService {

    private final ClienteRepositoryGateway repositoy;

    public GetClientByIdService(ClienteRepositoryGateway repositoy) {
        this.repositoy = repositoy;
    }

    public record GetClientByIdOutput(
        String id,
        String document,
        String name,
        LocalDate dateOfBirth,
        String phone,
        String address,
        LocalDateTime createdAt
    ){}

    
    public GetClientByIdOutput getById(String id){
        validateId(id);
        return repositoy
                .getById(id)
                .map(this::modelToOutput)
                .orElseThrow(() -> new ResourceNotFoundException("id '%s' not found".formatted(id)));
    }

    private void validateId(String id){
        if( id == null || id.isBlank() ) throw new ResourceNotFoundException("id is required");
    }

    private GetClientByIdOutput modelToOutput(ClientModel client){
        return new GetClientByIdOutput(
            client.getId(), 
            client.getDocument(), 
            client.getName(), 
            client.getDateOfBirth(), 
            client.getPhone(), 
            client.getAddress(),
            client.getCreatedAt()
        );
    }
}
