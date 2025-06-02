package demo.client.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import demo.client.application.exception.ResourceNotFoundException;
import demo.client.application.gateway.ClientRepositoryGateway;
import demo.client.application.model.ClientModel;



public class GetClientByIdService {

    private final ClientRepositoryGateway repositoy;

    public GetClientByIdService(ClientRepositoryGateway repositoy) {
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
