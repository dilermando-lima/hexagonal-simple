package demo.contract.application.gateway;

import java.time.LocalDate;

public interface RiskAnalisisServiceGateway {

    public record ClientAnalisisOutput(
        float score,
        String detail
    ){}

    public ClientAnalisisOutput analizeClient(String document, LocalDate dateOfBirth, String name);

}
