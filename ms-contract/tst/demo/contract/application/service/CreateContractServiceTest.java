package demo.contract.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import demo.contract.application.exception.InvalidRequestException;
import demo.contract.application.gateway.ContractRepositoryGateway;
import demo.contract.application.gateway.QuoteRepositoryGateway;
import demo.contract.application.gateway.RiskAnalisisServiceGateway;

@ExtendWith(MockitoExtension.class)
class CreateContractServiceTest extends ServiceFixture {

    @InjectMocks
    CreateContractService createContractService;

    @Mock ContractRepositoryGateway contractRepositoryGateway;
    @Mock QuoteRepositoryGateway quoteRepositoryGateway;
    @Mock RiskAnalisisServiceGateway riskAnalisisServiceGateway;


    @Test
    void GIVEN_quote_expired_WHEN_validateQuoteExpiration_SHOULD_throw_exception(){
        var inputValid = CREATE_CONTRACT_INPUT_VALID.get();
        var quoteModelValid = QUOTE_MODEL_VALID.get();

        // set expiration date to be expired
        quoteModelValid.setExpiresIn(LocalDateTime.now().minusDays(1));

        when(quoteRepositoryGateway.getById(inputValid.quoteId())).thenReturn(Optional.of(quoteModelValid));

        when(contractRepositoryGateway.existsByQuoteId(inputValid.quoteId())).thenReturn(false);

        assertThrows(InvalidRequestException.class, () -> createContractService.create(inputValid));
    }

    @Test
    void GIVEN_already_exists_by_quoteId_WHEN_validateAlreadyExistsByQuoteId_SHOULD_throw_exception(){
        var inputValid = CREATE_CONTRACT_INPUT_VALID.get();
        var quoteModelValid = QUOTE_MODEL_VALID.get();
        when(quoteRepositoryGateway.getById(inputValid.quoteId())).thenReturn(Optional.of(quoteModelValid));

        when(contractRepositoryGateway.existsByQuoteId(inputValid.quoteId())).thenReturn(true);

        assertThrows(InvalidRequestException.class, () -> createContractService.create(inputValid));
    }

    @Test
    void GIVEN_risk_analisis_has_score_less_then_0_7_WHEN_validateRiskAnalisis_SHOULD_throw_exception(){
        var inputValid = CREATE_CONTRACT_INPUT_VALID.get();
        var quoteModelValid = QUOTE_MODEL_VALID.get();
        when(quoteRepositoryGateway.getById(inputValid.quoteId())).thenReturn(Optional.of(quoteModelValid));

        when(contractRepositoryGateway.existsByQuoteId(inputValid.quoteId())).thenReturn(false);

        when(riskAnalisisServiceGateway.analizeClient(
            quoteModelValid.getClient().getDocument(), quoteModelValid.getClient().getDateOfBirth(),  quoteModelValid.getClient().getName()
        )).thenReturn(CLIENT_ANALISIS_OUTPUT_SCORE_0_69.get());

        assertThrows(InvalidRequestException.class, () -> createContractService.create(inputValid));
    }

    @Test
    void GIVEN_repository_returns_quote_empty_WHEN_getQuote_SHOULD_throw_exception(){
        var inputValid = CREATE_CONTRACT_INPUT_VALID.get();
        when(quoteRepositoryGateway.getById(inputValid.quoteId())).thenReturn(Optional.empty());

        assertThrows(InvalidRequestException.class, () -> createContractService.create(inputValid));
    }

    @Test
    void GIVEN_input_null_WHEN_validateInput_SHOULD_throw_exception(){
        var inputNull = CREATE_CONTRACT_INPUT_NULL.get();
        assertThrows(InvalidRequestException.class, () -> createContractService.create(inputNull));
    }

    @Test
    void GIVEN_input_with_id_null_WHEN_validateInput_SHOULD_throw_exception(){
        var inputWithIdNull = CREATE_CONTRACT_INPUT_WITH_ID_NULL.get(); 
        assertThrows(InvalidRequestException.class, () -> createContractService.create(inputWithIdNull));
    }

    @Test
    void GIVEN_input_with_id_blank_WHEN_validateInput_SHOULD_throw_exception(){
        var inputWithIdBlank = CREATE_CONTRACT_INPUT_WITH_ID_BLANK.get(); 
        assertThrows(InvalidRequestException.class, () -> createContractService.create(inputWithIdBlank));
    }

    
}
