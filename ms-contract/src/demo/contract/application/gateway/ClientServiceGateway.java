package demo.contract.application.gateway;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ClientServiceGateway {

    public record GetClientByIdOutput(
        String id,
        String document,
        String name,
        LocalDate dateOfBirth,
        String phone,
        String address,
        LocalDateTime createdAt
    ){}

    public Optional<GetClientByIdOutput> getById(String id);

}
