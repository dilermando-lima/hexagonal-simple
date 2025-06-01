package demo.contract.adapter.integration;

import java.time.LocalDate;

import demo.contract.application.gateway.RiskAnalisisServiceGateway;

public class RiskAnalisisServiceFake implements RiskAnalisisServiceGateway{

    @Override
    public ClientAnalisisOutput analizeClient(String document, LocalDate dateOfBirth, String name) {
        return new ClientAnalisisOutput(0.8F, "No comments");
    }
    
}
