package demo.customer.application.gateway;

import java.util.List;
import java.util.Optional;

import demo.customer.application.model.ClientModel;

public interface ClienteRepositoryGateway {

    public ClientModel insert(ClientModel client);
    public ClientModel update(ClientModel client);
    public List<ClientModel> list(String nameLK, String addressLK, String phoneEQ, String documentEQ);
    public Optional<ClientModel> getById(String id);
    public Optional<ClientModel> getByDocument(String document);
    public void deleteById(String id);

}
