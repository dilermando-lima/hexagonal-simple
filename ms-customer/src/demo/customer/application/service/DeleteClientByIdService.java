package demo.customer.application.service;

import demo.customer.application.gateway.ClienteRepositoryGateway;


public class DeleteClientByIdService {

    private final ClienteRepositoryGateway repositoy;
    private final GetClientByIdService getByIdService;

    public DeleteClientByIdService(ClienteRepositoryGateway repositoy, GetClientByIdService getByIdService) {
        this.repositoy = repositoy;
        this.getByIdService = getByIdService;
    }
    
    public void delete(String id){
        // .... ADD ANY STRATEGY FOR SOFT-DELETE
        var client = getByIdService.getById(id);
        repositoy.deleteById(client.id());
    }


}
