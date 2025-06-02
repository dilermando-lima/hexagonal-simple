package demo.client.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import demo.client.application.gateway.ClientRepositoryGateway;

@ExtendWith(MockitoExtension.class)
class DeleteClientByIdServiceTest extends ClientServiceFixture {

    @InjectMocks
    DeleteClientByIdService deleteClientByIdService;

    @Mock
    ClientRepositoryGateway clientRepositoryGateway;

    @Mock
    GetClientByIdService getClientByIdService;

    @Test
    void GIVEN_valid_input_WHEN_delete_SHOULD_return_valid_output(){

        var getClientByIdOutputValid = GET_CLIENT_BY_ID_OUTPUT_VALID.get();
 
        when(getClientByIdService.getById(getClientByIdOutputValid.id())).thenReturn(getClientByIdOutputValid);
        doNothing().when(clientRepositoryGateway).deleteById(getClientByIdOutputValid.id());

        assertDoesNotThrow(() -> deleteClientByIdService.delete(getClientByIdOutputValid.id()));

    }    
    
}
