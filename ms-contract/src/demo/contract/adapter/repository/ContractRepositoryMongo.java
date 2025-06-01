package demo.contract.adapter.repository;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import demo.contract.application.gateway.ContractRepositoryGateway;
import demo.contract.application.model.ContractModel;

@Repository
public interface ContractRepositoryMongo extends ContractRepositoryGateway, MongoRepository<ContractDocument, String>{

    @Override
    public default ContractModel insertNew(ContractModel quoteModel) {
        return Optional.of(quoteModel)
                    .map(this::toDocument)
                    .map(this::insert)
                    .map(this::toModel)
                    .get();
    }

    public default ContractDocument toDocument(ContractModel quoteModel){
        var document = new ContractDocument();
        BeanUtils.copyProperties(quoteModel, document);
        return document;
    }

    public default ContractModel toModel(ContractDocument document){
        var model = new ContractModel();
        BeanUtils.copyProperties(document, model);
        return model;
    }
    
}
