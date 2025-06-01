package demo.contract.application.service;

import org.springframework.beans.BeanUtils;

import demo.contract.application.exception.ResourceNotFoundException;
import demo.contract.application.gateway.PolicyRepositoryGateway;
import demo.contract.application.model.PolicyModel;

public class GetPolicyByIdService {

    private final PolicyRepositoryGateway policyRepositoryGateway;

    public GetPolicyByIdService(PolicyRepositoryGateway policyRepositoryGateway) {
        this.policyRepositoryGateway = policyRepositoryGateway;
    }

    public class GetPolicyByIdOutput extends PolicyModel{}

    public GetPolicyByIdOutput getById(String id){
        validateId(id);
        return policyRepositoryGateway
                .getById(id)
                .map(this::modelToOutput)
                .orElseThrow(() -> new ResourceNotFoundException("id '%s' not found".formatted(id)));
    }

    private GetPolicyByIdOutput modelToOutput(PolicyModel model) {
        var output = new GetPolicyByIdOutput();
        BeanUtils.copyProperties(model, output);
        return output;
    }

    private void validateId(String id){
        if( id == null || id.isBlank() ) throw new ResourceNotFoundException("id is required");
    }
}


