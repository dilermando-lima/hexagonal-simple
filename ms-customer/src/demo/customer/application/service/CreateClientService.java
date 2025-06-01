package demo.customer.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import demo.customer.application.exception.InvalidRequestException;
import demo.customer.application.gateway.ClienteRepositoryGateway;
import demo.customer.application.model.ClientModel;


public class CreateClientService {

    private final ClienteRepositoryGateway repositoy;

    public CreateClientService(ClienteRepositoryGateway repositoy) {
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
        repositoy
            .getByDocument(client.getDocument())
            .ifPresent(
                c -> { throw new InvalidRequestException("There is already a client with same document '%s'" .formatted(c.getDocument()));}
            );
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
