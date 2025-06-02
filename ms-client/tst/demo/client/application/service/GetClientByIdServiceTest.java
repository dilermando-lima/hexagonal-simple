package demo.client.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import demo.client.application.exception.ResourceNotFoundException;
import demo.client.application.gateway.ClientRepositoryGateway;

@ExtendWith(MockitoExtension.class)
class GetClientByIdServiceTest extends ClientServiceFixture {

    @InjectMocks
    GetClientByIdService getClientByIdService;

    @Mock
    ClientRepositoryGateway clientRepositoryGateway;

    @Test
    void GIVEN_valid_input_WHEN_getById_SHOULD_return_valid_output(){

        var clientModelValidWithId = CLIENT_MODEL_VALID_WITH_ID.get();
 
        when(clientRepositoryGateway.getById(clientModelValidWithId.getId())).thenReturn(Optional.of(clientModelValidWithId));
   
        var output = getClientByIdService.getById(clientModelValidWithId.getId());

        assertEquals(output.id(), clientModelValidWithId.getId());
        assertEquals(output.document(), clientModelValidWithId.getDocument());
        assertEquals(output.name(), clientModelValidWithId.getName());
        assertEquals(output.dateOfBirth(), clientModelValidWithId.getDateOfBirth());
        assertEquals(output.phone(), clientModelValidWithId.getPhone());
        assertEquals(output.address(), clientModelValidWithId.getAddress());
        assertEquals(output.createdAt(), clientModelValidWithId.getCreatedAt());

    }

        @Test
    void GIVEN_empty_return_from_respository_WHEN_getById_SHOULD_return_valid_output(){

        var anyIdValid = ANY_STRING_ID_VALID.get();
 
        when(clientRepositoryGateway.getById(anyIdValid)).thenReturn(Optional.empty());
   
        assertThrows(ResourceNotFoundException.class, () -> getClientByIdService.getById(anyIdValid));

    }

    @Test
    void GIVEN_id_null_WHEN_validateId_SHOULD_throw_exception(){
        var idNull = ANY_STRING_ID_NULL.get();
        assertThrows(ResourceNotFoundException.class, () -> getClientByIdService.getById(idNull));
    }

    @Test
    void GIVEN_id_blank_WHEN_validateId_SHOULD_throw_exception(){
        var idBlank = ANY_STRING_ID_BLANK.get();
        assertThrows(ResourceNotFoundException.class, () -> getClientByIdService.getById(idBlank));
    }
    
}
