package demo.contract.adapter.repository;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import demo.contract.application.gateway.QuoteRepositoryGateway;
import demo.contract.application.model.QuoteModel;

@Repository
public interface QuoteRepositoryMongo extends QuoteRepositoryGateway, MongoRepository<QuoteDocument, String>{

    @Override
    public default QuoteModel insertNew(QuoteModel quoteModel) {
        return Optional.of(quoteModel)
                    .map(this::toDocument)
                    .map(this::insert)
                    .map(this::toModel)
                    .get();
    }

    @Override
    default Optional<QuoteModel> getById(String id) {
        return findById(id).map(this::toModel);
    }

    public default QuoteDocument toDocument(QuoteModel quoteModel){
        var document = new QuoteDocument();
        BeanUtils.copyProperties(quoteModel, document);
        return document;
    }

    public default QuoteModel toModel(QuoteDocument document){
        var model = new QuoteModel();
        BeanUtils.copyProperties(document, model);
        return model;
    }
    
}
