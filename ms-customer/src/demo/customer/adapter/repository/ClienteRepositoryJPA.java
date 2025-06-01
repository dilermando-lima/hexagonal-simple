package demo.customer.adapter.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import demo.customer.application.gateway.ClienteRepositoryGateway;
import demo.customer.application.model.ClientModel;


@Repository
public interface ClienteRepositoryJPA extends ClienteRepositoryGateway , CrudRepository<ClientEntity,String> {

    @SuppressWarnings({"java:S3655"})
    @Override
    public default ClientModel insert(ClientModel client) {
        return Optional.of(client)
            .map(this::toEntity)
            .map(this::save)
            .map(this::toModel)
            .get();
    }

    @SuppressWarnings({"java:S3655"})
    @Override
    public default ClientModel update(ClientModel client) {
        return Optional.of(client)
            .map(this::toEntity)
            .map(this::save)
            .map(this::toModel)
            .get();
    }


    @Query("""
    SELECT c FROM demo.customer.adapter.repository.ClientEntity c WHERE 
        ( :nameLK IS NULL OR c.name LIKE %:nameLK%          ) AND
        ( :addressLK IS NULL OR c.address LIKE %:addressLK% ) AND
        ( :phoneEQ IS NULL OR c.phone = :phoneEQ            ) AND
        ( :documentEQ IS NULL OR c.document = :documentEQ   )
    """)
    public List<ClientEntity> listEntity(
        @Param("nameLK") String nameLK, 
        @Param("addressLK") String addressLK, 
        @Param("phoneEQ") String phoneEQ, 
        @Param("documentEQ") String documentEQ
    );

    public Optional<ClientEntity> findByDocument(String document);

    @SuppressWarnings("java:S6204")
    @Override
    public default List<ClientModel> list(String nameLK, String addressLK, String phoneEQ, String documentEQ) {
        return listEntity(nameLK, addressLK, phoneEQ, documentEQ).stream(). map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public default Optional<ClientModel> getById(String id) {
        return findById(id).map(this::toModel);
    }

    @Override
    public default Optional<ClientModel> getByDocument(String document) {
        return findByDocument(document).map(this::toModel);
    }

    @Override
    public default void deleteById(String id) {
        findById(id).ifPresent(this::delete);
    }

    public default ClientModel toModel(ClientEntity entity){
        ClientModel model = new ClientModel();
        model.setId(entity.getId());
        model.setDocument(entity.getDocument());
        model.setName(entity.getName());
        model.setDateOfBirth(entity.getDateOfBirth());
        model.setPhone(entity.getPhone());
        model.setAddress(entity.getAddress());
        model.setCreatedAt(entity.getCreatedAt());
        return model;
    }

    public default ClientEntity toEntity(ClientModel model){
        ClientEntity entity = new ClientEntity();
        entity.setId(model.getId());
        entity.setDocument(model.getDocument());
        entity.setName(model.getName());
        entity.setDateOfBirth(model.getDateOfBirth());
        entity.setPhone(model.getPhone());
        entity.setAddress(model.getAddress());
        entity.setCreatedAt(model.getCreatedAt());
        return entity;
    }
    
}
