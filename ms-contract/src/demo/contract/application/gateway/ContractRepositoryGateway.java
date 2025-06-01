package demo.contract.application.gateway;

import demo.contract.application.model.ContractModel;

public interface ContractRepositoryGateway {

    public ContractModel insertNew(ContractModel contract);
    public boolean existsByQuoteId(String id);

}
