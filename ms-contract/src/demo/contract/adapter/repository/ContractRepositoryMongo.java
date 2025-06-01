package demo.contract.adapter.repository;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import demo.contract.application.gateway.ContractRepositoryGateway;
import demo.contract.application.model.ContractModel;
import demo.contract.application.model.QuoteModel;

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

    boolean existsByQuote_Id(String quoteId);

    @Override
    default boolean existsByQuoteId(String quoteId) {
       return existsByQuote_Id(quoteId);
    }

    public default ContractDocument toDocument(ContractModel quoteModel){

        var contractDocument = new ContractDocument();
        BeanUtils.copyProperties(quoteModel, contractDocument);

        var quoteDocument = new QuoteDocument();
        quoteDocument.setId(quoteModel.getQuote().getId());
        
        contractDocument.setQuote(quoteDocument);

        return contractDocument;
    }

    public default ContractModel toModel(ContractDocument contractDocument){
        
        var contractModel = new ContractModel();
        BeanUtils.copyProperties(contractDocument, contractModel);

        var quoteModel = new QuoteModel();
        BeanUtils.copyProperties(contractDocument.getQuote(), quoteModel);

        contractModel.setQuote(quoteModel);
        return contractModel;
    }
    
}
