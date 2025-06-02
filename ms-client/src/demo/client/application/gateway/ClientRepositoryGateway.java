package demo.client.application.gateway;

import java.util.List;
import java.util.Optional;

import demo.client.application.model.ClientModel;

public interface ClientRepositoryGateway {

    public ClientModel insert(ClientModel client);
    public ClientModel update(ClientModel client);
    public List<ClientModel> list(String nameLK, String addressLK, String phoneEQ, String documentEQ);
    public Optional<ClientModel> getById(String id);
    public boolean existsByDocument(String document);
    public void deleteById(String id);

}
