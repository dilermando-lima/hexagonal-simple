package demo.client.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import demo.client.application.exception.InvalidRequestException;
import demo.client.application.gateway.ClientRepositoryGateway;
import demo.client.application.model.ClientModel;

@ExtendWith(MockitoExtension.class)
class CreateClientServiceTest extends ClientServiceFixture {

    @InjectMocks
    CreateClientService createClientService;

    @Mock
    ClientRepositoryGateway clientRepositoryGateway;

    @Test
    void GIVEN_valid_input_WHEN_insert_SHOULD_return_valid_output(){

        var clientModelValidWithId = CLIENT_MODEL_VALID_WITH_ID.get();
        var validInput = CREATE_CLIENT_INPUT_VALID.get();
        
        when(clientRepositoryGateway.existsByDocument(anyString())).thenReturn(false);
        when(clientRepositoryGateway.insert(any(ClientModel.class))).thenReturn(clientModelValidWithId);

        var output = createClientService.insert(validInput);

        assertEquals(output.newId(), clientModelValidWithId.getId());

    }

    static Stream<Supplier<CreateClientService.CreateClientInput>> all_invalid_request_scenarios_WHEN_validateInput(){
        return Stream.of(
            CREATE_CLIENT_INPUT_NULL,
            CREATE_CLIENT_INPUT_WITH_NAME_NULL,
            CREATE_CLIENT_INPUT_WITH_DOCUMENT_NULL,
            CREATE_CLIENT_INPUT_WITH_DOCUMENT_INVALID,
            CREATE_CLIENT_INPUT_WITH_ADDRESS_NULL,
            CREATE_CLIENT_INPUT_WITH_DATE_OF_BIRTH_NULL,
            CREATE_CLIENT_INPUT_WITH_PHONE_NULL,
            CREATE_CLIENT_INPUT_WITH_NAME_BLANK,
            CREATE_CLIENT_INPUT_WITH_DOCUMENT_BLANK,
            CREATE_CLIENT_INPUT_WITH_ADDRESS_BLANK,
            CREATE_CLIENT_INPUT_WITH_PHONE_BLANK
        );
    }
    @ParameterizedTest
    @MethodSource("all_invalid_request_scenarios_WHEN_validateInput")
    void GIVEN_all_invalid_request_scenarios_WHEN_validateInput_SHOULD_throw_InvalidRequestException(
        Supplier<CreateClientService.CreateClientInput> createClientInputSupplier
    ){
        var input = createClientInputSupplier.get();
        assertThrows(InvalidRequestException.class, () -> createClientService.insert(input));
    }


    @Test
    void GIVEN_already_exists_with_same_document_WHEN_validateDocumentAlreadyExists_SHOULD_throws_exception(){

        when(clientRepositoryGateway.existsByDocument(anyString())).thenReturn(true);

        var validInput = CREATE_CLIENT_INPUT_VALID.get();

        assertThrows(InvalidRequestException.class, () -> createClientService.insert(validInput));

    }

    @Test
    void GIVEN_not_exists_with_same_document_WHEN_validateDocumentAlreadyExists_SHOULD_run_OK(){

        when(clientRepositoryGateway.existsByDocument(anyString())).thenReturn(false);
        when(clientRepositoryGateway.insert(any(ClientModel.class))).thenReturn(new ClientModel());

        var validInput = CREATE_CLIENT_INPUT_VALID.get();

        assertDoesNotThrow(() -> createClientService.insert(validInput));

    }

    
    
}
