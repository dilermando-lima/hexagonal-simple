package demo.client.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import demo.client.application.exception.InvalidRequestException;
import demo.client.application.gateway.ClientRepositoryGateway;
import demo.client.application.model.ClientModel;


public class CreateClientService {

    private final ClientRepositoryGateway repositoy;

    public CreateClientService(ClientRepositoryGateway repositoy) {
        this.repositoy = repositoy;
    }

    public record CreateClientInput(
        String document,
        String name,
        LocalDate dateOfBirth,
        String phone,
        String address
    ){}

    public record CreateClientOutput(
        String newId
    ){}
    
    public CreateClientOutput insert(CreateClientInput input){
        validateInput(input);
        var client = inputToModel(input);
        validateDocumentAlreadyExists(client);
        return modelToOutput(repositoy.insert(client));
    }

    private ClientModel inputToModel(CreateClientInput input){
        var clientModel = new ClientModel();
        clientModel.setId(null);
        clientModel.setCreatedAt(LocalDateTime.now());
        clientModel.setDocument(input.document.replaceAll("\\D", ""));
        clientModel.setAddress(input.address);
        clientModel.setDateOfBirth(input.dateOfBirth);
        clientModel.setName(input.name);
        clientModel.setPhone(input.phone);
        return clientModel;
    }

    private CreateClientOutput modelToOutput(ClientModel client){
        return new CreateClientOutput(client.getId());
    }

    private void validateDocumentAlreadyExists(ClientModel client){
        if(repositoy.existsByDocument(client.getDocument()))
            throw new InvalidRequestException("There is already a client with same document '%s'" .formatted(client.getDocument()));
    }

    private void validateInput(CreateClientInput input){

        if( input == null ) throw new InvalidRequestException("input cannot be empty");
        if( input.name == null || input.name.isBlank()) throw new InvalidRequestException("name is required");
        if( input.document == null || input.document.isBlank()) throw new InvalidRequestException("document is required");
        if( input.document.replaceAll("\\D", "").length() < 11 ) throw new InvalidRequestException("document is invalid"); // DO NOT USE THAT IN PRODUCTION
        if( input.address == null || input.address.isBlank()) throw new InvalidRequestException("address is required");
        if( input.dateOfBirth == null ) throw new InvalidRequestException("date of birth is required");
        if( input.phone == null || input.phone.isBlank()) throw new InvalidRequestException("phone is required");

        // .... ADD HERE FURTHER VALIDATIONS OR MOVE ALL LOGIC TO ANY ABSTRACTION OF VALIDATION

    }    

}
