package demo.contract.application.gateway;

import java.util.Optional;

import demo.contract.application.model.QuoteModel;

public interface QuoteRepositoryGateway {

    public Optional<QuoteModel> getById(String id);
    public QuoteModel insertNew(QuoteModel quote);

}
