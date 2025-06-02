package demo.client.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import demo.client.application.gateway.ClientRepositoryGateway;
import demo.client.application.model.ClientModel;

@ExtendWith(MockitoExtension.class)
class ListClientServiceTest extends ClientServiceFixture {

    @InjectMocks
    ListClientService listClientService;

    @Mock
    ClientRepositoryGateway clientRepositoryGateway;

    @Test
    void GIVEN_valid_input_params_WHEN_list_SHOULD_return_valid_output_list(){

        var clientModelList = List.of(CLIENT_MODEL_VALID_WITH_ID.get());
 
        when(clientRepositoryGateway.list(anyString(), anyString(), anyString(), anyString())).thenReturn(clientModelList);

        var outputList = listClientService.list("", "", "", "");

        assertEquals(1, outputList.size());

        assertEquals(outputList.get(0).id(), clientModelList.get(0).getId());
        assertEquals(outputList.get(0).document(), clientModelList.get(0).getDocument());
        assertEquals(outputList.get(0).name(), clientModelList.get(0).getName());

    }    

    @Test
    void GIVEN_repository_returns_empty_list_WHEN_list_SHOULD_return_empty_output_list(){

        var emptyClientModelList = new ArrayList<ClientModel>();
 
        when(clientRepositoryGateway.list(anyString(), anyString(), anyString(), anyString())).thenReturn(emptyClientModelList);

        var outputList = listClientService.list("", "", "", "");

        assertEquals(0, outputList.size());
        
    } 
    
}
