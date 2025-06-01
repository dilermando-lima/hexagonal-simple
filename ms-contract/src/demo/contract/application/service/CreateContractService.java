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

    public record CreateContractOutput(String newId){}

    public record CreateContractInput(String quoteId){}

    public CreateContractOutput create(CreateContractInput input){
        validateInput(input);
        var quote = getQuote(input);
        validateAlreadyExistsByQuoteId(input);
        validateQuoteExpiration(quote);
        validateRiskAnalisis(quote.getClient());
        var contract = contractRepositoryGateway.insertNew(inputToModel(quote));
        return new CreateContractOutput(contract.getId());
    }

    private void validateQuoteExpiration(QuoteModel quote) {
        if( quote.getExpiresIn().isAfter(LocalDateTime.now()))
            throw new InvalidRequestException("Quote id '%s' is expired");
    }

    private void validateAlreadyExistsByQuoteId(CreateContractInput input) {
        if(contractRepositoryGateway.existsByQuoteId(input.quoteId))
            throw new InvalidRequestException("Quote id '%s' is already added into specific contract");
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

    private QuoteModel getQuote(CreateContractInput input) {
        return quoteRepositoryGateway
            .getById(input.quoteId)
            .orElseThrow(() -> new InvalidRequestException("Quote id '%s' is invalid".formatted(input.quoteId)));
    }

    private void validateInput(CreateContractInput input) {
        if(input == null) throw new InvalidRequestException("request is empty");
        if(input.quoteId == null || input.quoteId.isBlank()) throw new InvalidRequestException("quoteId is required");
    }
    
}
