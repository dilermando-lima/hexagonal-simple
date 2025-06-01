package demo.contract.application.service;

import java.time.LocalDateTime;

import demo.contract.application.exception.InvalidRequestException;
import demo.contract.application.gateway.ClientServiceGateway;
import demo.contract.application.gateway.PolicyRepositoryGateway;
import demo.contract.application.gateway.QuoteRepositoryGateway;
import demo.contract.application.gateway.RiskAnalisisServiceGateway;
import demo.contract.application.model.ClientModel;
import demo.contract.application.model.PolicyModel;
import demo.contract.application.model.QuoteModel;

public class CreateQuoteService {

    private static final int LIMIT_EXPIRATION_QUOTE_IN_DAYS = 15; // .... MOVE TO DYNAMIC CONFIGURATION STRATEGY OR SOME BUSINESS RULE

    private final PolicyRepositoryGateway policyRepositoryGateway;
    private final QuoteRepositoryGateway quoteRepositoryGateway;
    private final ClientServiceGateway clientServiceGateway;
    private final RiskAnalisisServiceGateway riskAnalisisServiceGateway;

    public CreateQuoteService(
        PolicyRepositoryGateway policyRepositoryGateway, 
        ClientServiceGateway clientServiceGateway,
        QuoteRepositoryGateway quoteRepositoryGateway, 
        RiskAnalisisServiceGateway riskAnalisisServiceGateway
    ) {
        this.policyRepositoryGateway = policyRepositoryGateway;
        this.quoteRepositoryGateway = quoteRepositoryGateway;
        this.clientServiceGateway = clientServiceGateway;
        this.riskAnalisisServiceGateway = riskAnalisisServiceGateway;
    }

    public record CreateOutput(String newId){}

    public record CreateInput(String clientId, String policyId){}

    public CreateOutput create(CreateInput input){
        validateInput(input);
        var client = getClient(input);
        var policy = getPolicy(input);
        validateRiskAnalisis(client);
        var quote = quoteRepositoryGateway.insertNew(inputToModel(client, policy));
        return new CreateOutput(quote.getId());
    }

    private QuoteModel inputToModel(ClientModel client, PolicyModel policy) {
        var quote =  new QuoteModel();
        quote.setClient(client);
        quote.setCreatedAt(LocalDateTime.now());
        quote.setPolicy(policy);
        quote.setExpiresIn(LocalDateTime.now().plusDays(LIMIT_EXPIRATION_QUOTE_IN_DAYS));
        return quote;
    }

    private void validateRiskAnalisis(ClientModel client) {
        var outputRisk = riskAnalisisServiceGateway.analizeClient(client.getDocument(), client.getDateOfBirth(), client.getName());
        if( outputRisk.score() < 0.7 ){
            throw new InvalidRequestException("Got reproved on Risk validation: %s".formatted(outputRisk.detail()));
        }
    }

    private PolicyModel getPolicy(CreateInput input) {
        return policyRepositoryGateway
            .getById(input.policyId)
            .orElseThrow(() -> new InvalidRequestException("Policy id '%s' is invalid".formatted(input.policyId)));
    }

    private ClientModel getClient(CreateInput input) {
        return clientServiceGateway
            .getById(input.clientId)
            .map(output -> {
                var client =  new ClientModel();
                client.setAddress(output.address());
                client.setDateOfBirth(output.dateOfBirth());
                client.setDocument(output.document());
                client.setRegistryId(output.id());
                client.setName(output.name());
                client.setPhone(output.phone());
                return client;
            })
            .orElseThrow(() -> new InvalidRequestException("Client id '%s' is invalid".formatted(input.clientId)));
    }

    private void validateInput(CreateInput input) {
        if(input == null) throw new InvalidRequestException("request is empty");
        if(input.clientId == null || input.clientId.isBlank()) throw new InvalidRequestException("clientId is required");
        if(input.policyId == null || input.policyId.isBlank()) throw new InvalidRequestException("policyId is required");
    }
    
    
}
