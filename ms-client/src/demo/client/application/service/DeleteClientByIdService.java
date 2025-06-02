package demo.client.application.service;

import demo.client.application.gateway.ClientRepositoryGateway;


public class DeleteClientByIdService {

    private final ClientRepositoryGateway repositoy;
    private final GetClientByIdService getClientByIdService;

    public DeleteClientByIdService(ClientRepositoryGateway repositoy, GetClientByIdService getClientByIdService) {
        this.repositoy = repositoy;
        this.getClientByIdService = getClientByIdService;
    }
    
    public void delete(String id){
        // .... ADD ANY STRATEGY FOR SOFT-DELETE
        var client = getClientByIdService.getById(id);
        repositoy.deleteById(client.id());
    }


}
