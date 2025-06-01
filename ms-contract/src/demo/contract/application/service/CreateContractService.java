package demo.contract.application.service;

import java.time.LocalDateTime;

import demo.contract.application.exception.InvalidRequestException;
import demo.contract.application.gateway.ContractRepositoryGateway;
import demo.contract.application.gateway.QuoteRepositoryGateway;
import demo.contract.application.gateway.RiskAnalisisServiceGateway;
import demo.contract.application.model.ClientModel;
import demo.contract.application.model.ContractModel;
import demo.contract.application.model.QuoteModel;

public class CreateContractService {

    private static final int LIMIT_EXPIRATION_CONTRACT_IN_DAYS = 365; // .... MOVE TO DYNAMIC CONFIGURATION STRATEGY OR SOME BUSINESS RULE

    private final ContractRepositoryGateway contractRepositoryGateway;
    private final QuoteRepositoryGateway quoteRepositoryGateway;
    private final RiskAnalisisServiceGateway riskAnalisisServiceGateway;

    public CreateContractService(
        ContractRepositoryGateway contractRepositoryGateway, 
        QuoteRepositoryGateway quoteRepositoryGateway, 
        RiskAnalisisServiceGateway riskAnalisisServiceGateway
    ) {
        this.contractRepositoryGateway = contractRepositoryGateway;
        this.quoteRepositoryGateway = quoteRepositoryGateway;
        this.riskAnalisisServiceGateway = riskAnalisisServiceGateway;
    }

    public record CreateOutput(String newId){}

    public record CreateInput(String quoteId){}

    public CreateOutput create(CreateInput input){
        validateInput(input);
        var quote = getQuote(input);
        validateRiskAnalisis(quote.getClient());
        var contract = contractRepositoryGateway.insertNew(inputToModel(quote));
        return new CreateOutput(contract.getId());
    }

    private ContractModel inputToModel(QuoteModel quote) {
        var contract =  new ContractModel();
        contract.setQuote(quote);
        contract.setStartedAt(LocalDateTime.now());
        contract.setExpiresIn(LocalDateTime.now().plusDays(LIMIT_EXPIRATION_CONTRACT_IN_DAYS));
        return contract;
    }

    private void validateRiskAnalisis(ClientModel client) {
        var outputRisk = riskAnalisisServiceGateway.analizeClient(client.getDocument(), client.getDateOfBirth(), client.getName());
        if( outputRisk.score() < 0.7 ){
            throw new InvalidRequestException("Got reproved on Risk validation: %s".formatted(outputRisk.detail()));
        }
    }

    private QuoteModel getQuote(CreateInput input) {
        return quoteRepositoryGateway
            .getById(input.quoteId)
            .orElseThrow(() -> new InvalidRequestException("Quote id '%s' is invalid".formatted(input.quoteId)));
    }

    private void validateInput(CreateInput input) {
        if(input == null) throw new InvalidRequestException("request is empty");
        if(input.quoteId == null || input.quoteId.isBlank()) throw new InvalidRequestException("quoteId is required");
    }
    
}
