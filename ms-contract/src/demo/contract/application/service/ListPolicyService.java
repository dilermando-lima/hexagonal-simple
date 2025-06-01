package demo.contract.application.service;

import java.util.List;

import demo.contract.application.gateway.PolicyRepositoryGateway;
import demo.contract.application.model.PolicyModel;

public class ListPolicyService {

    private final PolicyRepositoryGateway policyRepositoryGateway;

    public ListPolicyService(PolicyRepositoryGateway policyRepositoryGateway) {
        this.policyRepositoryGateway = policyRepositoryGateway;
    }

    public record PolicyOutputItem(
        String id,
        String name,
        String description
    ){}

    public List<PolicyOutputItem> listAll(){
        return policyRepositoryGateway.listAll().stream().map(this::modelToOutput).toList();
    }

    private PolicyOutputItem modelToOutput(PolicyModel model){
        return new PolicyOutputItem(model.getId(), model.getName(), model.getDescription());
    }
}
