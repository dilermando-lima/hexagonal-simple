package demo.contract.adapter.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import demo.contract.application.gateway.PolicyRepositoryGateway;
import demo.contract.application.model.CoverageModel;
import demo.contract.application.model.PolicyModel;

@Component
public class PolicyRepositoryCatalogFake implements PolicyRepositoryGateway{

    private static final Map<String,PolicyModel> POLICY_MAP_FAKE = Map.of(
            "1",
            new PolicyModel("1", "Bronze", "O seguro Bronze possui...",
                List.of(
                    new CoverageModel("Cobertura 1", "...", BigDecimal.valueOf(10000)),
                    new CoverageModel("Cobertura 2", "...", BigDecimal.valueOf(4600))
                )
            ),
            "2",
            new PolicyModel("2", "Prata", "O seguro Prata possui...",
                List.of(
                    new CoverageModel("Cobertura 3", "...", BigDecimal.valueOf(15000)),
                    new CoverageModel("Cobertura 4", "...", BigDecimal.valueOf(8000))
                )
            ),
            "3",
            new PolicyModel("3", "Ouro", "O seguro Ouro possui...",
                List.of(
                    new CoverageModel("Cobertura 5", "...", BigDecimal.valueOf(30000)),
                    new CoverageModel("Cobertura 6", "...", BigDecimal.valueOf(15000))
                )
            )
        );

    @Override
    public List<PolicyModel> listAll() {
        return POLICY_MAP_FAKE.values().stream().toList();
    }

    @Override
    public Optional<PolicyModel> getById(String id) {
        var policy = POLICY_MAP_FAKE.get(id);
        if(policy == null){
            return Optional.empty();
        }else{
            return Optional.of(policy);
        }
    }
    
}
