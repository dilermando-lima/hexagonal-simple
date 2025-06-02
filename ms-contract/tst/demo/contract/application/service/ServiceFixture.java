package demo.contract.application.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import demo.contract.application.gateway.RiskAnalisisServiceGateway.ClientAnalisisOutput;
import demo.contract.application.model.ClientModel;
import demo.contract.application.model.CoverageModel;
import demo.contract.application.model.PolicyModel;
import demo.contract.application.model.QuoteModel;
import demo.contract.application.service.CreateContractService.CreateContractInput;

public abstract class ServiceFixture {

    protected static final Supplier<CreateContractService.CreateContractInput> CREATE_CONTRACT_INPUT_NULL = () -> null;

    protected static final Supplier<CreateContractService.CreateContractInput> CREATE_CONTRACT_INPUT_WITH_ID_NULL = () ->
        new CreateContractInput(null);

    protected static final Supplier<CreateContractService.CreateContractInput> CREATE_CONTRACT_INPUT_WITH_ID_BLANK = () ->
        new CreateContractInput("     ");
    
    protected static final Supplier<CreateContractService.CreateContractInput> CREATE_CONTRACT_INPUT_VALID = () ->
        new CreateContractInput(UUID.randomUUID().toString());


    protected static final Supplier<ClientAnalisisOutput> CLIENT_ANALISIS_OUTPUT_SCORE_0_69 = () ->  new ClientAnalisisOutput(0.69F, null);

    protected static final Supplier<ClientAnalisisOutput> CLIENT_ANALISIS_OUTPUT_SCORE_0_9 = () ->  new ClientAnalisisOutput(0.9F, null);
        
    protected static final Supplier<QuoteModel> QUOTE_MODEL_VALID = () -> {

        ClientModel client = new ClientModel();
        client.setRegistryId(UUID.randomUUID().toString());
        client.setDocument("12043678900");
        client.setName("John Doe");
        client.setDateOfBirth(LocalDate.of(1990, 1, 15));
        client.setPhone("11999998888");
        client.setAddress("123 Main St, São Paulo");

        PolicyModel policy = new PolicyModel();
        policy.setId("pol1");
        policy.setName("Standard Policy");
        policy.setDescription("Standard insurance policy");
        policy.setCoverages(List.of(new CoverageModel("Cobertura 1", "...", BigDecimal.valueOf(10000))));

        QuoteModel quote = new QuoteModel();
        quote.setId(UUID.randomUUID().toString());
        quote.setClient(client);
        quote.setPolicy(policy);
        quote.setCreatedAt(LocalDateTime.now());
        quote.setExpiresIn(LocalDateTime.now().plusDays(CreateQuoteService.LIMIT_EXPIRATION_QUOTE_IN_DAYS + 1));

        return quote;

    };
        

    
}
