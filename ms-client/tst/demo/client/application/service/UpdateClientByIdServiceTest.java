package demo.client.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
class UpdateClientByIdServiceTest extends ClientServiceFixture {

    @InjectMocks
    UpdateClientByIdService updateClientByIdService;

    @Mock
    ClientRepositoryGateway clientRepositoryGateway;

    @Mock
    GetClientByIdService getClientByIdService;

    @Test
    void GIVEN_valid_input_WHEN_update_SHOULD_return_valid_output(){

        var getClientByIdOutputValid = GET_CLIENT_BY_ID_OUTPUT_VALID.get();
        var updateClientIdInputValid = UPDATE_CLIENT_BY_ID_INPUT_VALID.get();
        var clientModelValid = CLIENT_MODEL_VALID_WITH_ID.get();
 
        when(getClientByIdService.getById(getClientByIdOutputValid.id())).thenReturn(getClientByIdOutputValid);
        when(clientRepositoryGateway.update(any(ClientModel.class))).thenReturn(clientModelValid);

        assertDoesNotThrow(() -> updateClientByIdService.update(getClientByIdOutputValid.id(), updateClientIdInputValid));

    }

    static Stream<Supplier<UpdateClientByIdService.UpdateClientByIdInput>> all_invalid_request_scenarios_WHEN_validateInput(){
        return Stream.of(
            UPDATE_CLIENT_BY_ID_INPUT_NULL,
            UPDATE_CLIENT_BY_ID_INPUT_WITH_NAME_NULL,
            UPDATE_CLIENT_BY_ID_INPUT_WITH_ADDRESS_NULL,
            UPDATE_CLIENT_BY_ID_INPUT_WITH_DATE_OF_BIRTH_NULL,
            UPDATE_CLIENT_BY_ID_INPUT_WITH_PHONE_NULL,
            UPDATE_CLIENT_BY_ID_INPUT_WITH_NAME_BLANK,
            UPDATE_CLIENT_BY_ID_INPUT_WITH_PHONE_BLANK
        );
    }
    @ParameterizedTest
    @MethodSource("all_invalid_request_scenarios_WHEN_validateInput")
    void GIVEN_all_invalid_request_scenarios_WHEN_validateInput_SHOULD_throw_InvalidRequestException(
        Supplier<UpdateClientByIdService.UpdateClientByIdInput> createClientInputSupplier
    ){
        var input = createClientInputSupplier.get();
        var anyId = ANY_STRING_ID_VALID.get();
        assertThrows(InvalidRequestException.class, () -> updateClientByIdService.update(anyId, input));
    }

}
