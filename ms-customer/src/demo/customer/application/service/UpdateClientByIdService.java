package demo.customer.application.service;

import java.time.LocalDate;

import demo.customer.application.exception.InvalidRequestException;
import demo.customer.application.gateway.ClienteRepositoryGateway;
import demo.customer.application.model.ClientModel;
import demo.customer.application.service.GetClientByIdService.GetClientByIdOutput;


public class UpdateClientByIdService {

    private final ClienteRepositoryGateway repositoy;
    private final GetClientByIdService getByIdService;

    public UpdateClientByIdService(ClienteRepositoryGateway repositoy, GetClientByIdService getByIdService) {
        this.repositoy = repositoy;
        this.getByIdService = getByIdService;
    }
    
    public record UpdateClientByIdInput(
        String name,
        LocalDate dateOfBirth,
        String phone,
        String address
    ){}

    public void update(String id, UpdateClientByIdInput input){
        validateInput(input);
        var client = getByIdService.getById(id);
        var clientToUpdate = inputToModel(input, client);
        repositoy.update(clientToUpdate);
    }

    private void validateInput(UpdateClientByIdInput input){

        if( input == null ) throw new InvalidRequestException("request cannot be empty");
        if( input.name == null || input.name.isBlank()) throw new InvalidRequestException("name is required");
        if( input.address == null || input.address.isBlank()) throw new InvalidRequestException("address is required");
        if( input.dateOfBirth == null ) throw new InvalidRequestException("date of birth is required");
        if( input.phone == null || input.phone.isBlank()) throw new InvalidRequestException("phone is required");

        // .... ADD HERE FURTHER VALIDATIONS OR MOVE ALL LOGIC TO ANY ABSTRACTION OF VALIDATION

    }

    private ClientModel inputToModel(UpdateClientByIdInput input, GetClientByIdOutput client){
        var clientModel = new ClientModel();
        clientModel.setId(client.id());
        clientModel.setCreatedAt(client.createdAt());
        clientModel.setDocument(client.document());
        clientModel.setAddress(input.address);
        clientModel.setDateOfBirth(input.dateOfBirth);
        clientModel.setName(input.name);
        clientModel.setPhone(input.phone);
        return clientModel;
    }
    

}
