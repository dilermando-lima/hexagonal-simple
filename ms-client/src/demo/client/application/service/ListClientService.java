package demo.client.application.service;

import java.util.List;

import demo.client.application.gateway.ClientRepositoryGateway;
import demo.client.application.model.ClientModel;


public class ListClientService {

    private final ClientRepositoryGateway repositoy;

    public ListClientService(ClientRepositoryGateway repositoy) {
        this.repositoy = repositoy;
    }
    
    public record ClientOutputItem(
        String id,
        String document,
        String name
    ){}

    public List<ClientOutputItem> list(String nameLK, String addressLK, String phoneEQ, String documentEQ){
        // .... ADD HERE PAGINATION AND PROJECTION STRATEGIES
        return repositoy
                .list(nameLK, addressLK, phoneEQ, documentEQ)
                .stream()
                .map(this::modelToOutputItem)
                .toList();
    }

    private ClientOutputItem modelToOutputItem(ClientModel client){
        return new ClientOutputItem(
            client.getId(), 
            client.getDocument(), 
            client.getName()
        );
    }

    

}
