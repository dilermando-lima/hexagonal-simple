package demo.contract.application.service;

import java.util.List;

import demo.contract.application.gateway.PolicyRepositoryGateway;
import demo.contract.application.model.PolicyModel;

public class ListPolicyService {

    private final PolicyRepositoryGateway policyRepositoryGateway;

    public ListPolicyService(PolicyRepositoryGateway policyRepositoryGateway) {
        this.policyRepositoryGateway = policyRepositoryGateway;
    }

    public record OutputItem(
        String id,
        String name,
        String description
    ){}

    public List<OutputItem> listAll(){
        return policyRepositoryGateway.listAll().stream().map(this::modelToOutput).toList();
    }

    private OutputItem modelToOutput(PolicyModel model){
        return new OutputItem(model.getId(), model.getName(), model.getDescription());
    }
}
