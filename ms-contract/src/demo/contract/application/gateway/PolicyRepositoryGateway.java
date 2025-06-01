package demo.contract.application.gateway;

import java.util.List;
import java.util.Optional;

import demo.contract.application.model.PolicyModel;

public interface PolicyRepositoryGateway {

    public List<PolicyModel> listAll();
    public Optional<PolicyModel> getById(String id);

}
