package demo.client.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

import demo.client.application.model.ClientModel;
import demo.client.application.service.CreateClientService.CreateClientInput;
import demo.client.application.service.GetClientByIdService.GetClientByIdOutput;
import demo.client.application.service.UpdateClientByIdService.UpdateClientByIdInput;

public abstract class ClientServiceFixture {

    protected static final Supplier<UpdateClientByIdService.UpdateClientByIdInput> UPDATE_CLIENT_BY_ID_INPUT_VALID = () ->
        new UpdateClientByIdInput("John Doe", LocalDate.of(1990, 1, 1), "1234567890", "123 Main St");

    protected static final Supplier<UpdateClientByIdService.UpdateClientByIdInput> UPDATE_CLIENT_BY_ID_INPUT_NULL = () -> null;

    protected static final Supplier<UpdateClientByIdService.UpdateClientByIdInput> UPDATE_CLIENT_BY_ID_INPUT_WITH_NAME_NULL = () ->
        new UpdateClientByIdInput(null, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St");


    protected static final Supplier<UpdateClientByIdService.UpdateClientByIdInput> UPDATE_CLIENT_BY_ID_INPUT_WITH_ADDRESS_NULL = () ->
        new UpdateClientByIdInput("John Doe", LocalDate.of(1990, 1, 1), "1234567890", null);

    protected static final Supplier<UpdateClientByIdService.UpdateClientByIdInput> UPDATE_CLIENT_BY_ID_INPUT_WITH_DATE_OF_BIRTH_NULL = () ->
        new UpdateClientByIdInput("John Doe", null, "1234567890", "123 Main St");

    protected static final Supplier<UpdateClientByIdService.UpdateClientByIdInput> UPDATE_CLIENT_BY_ID_INPUT_WITH_PHONE_NULL = () ->
        new UpdateClientByIdInput("John Doe", LocalDate.of(1990, 1, 1), null, "123 Main St");

    protected static final Supplier<UpdateClientByIdService.UpdateClientByIdInput> UPDATE_CLIENT_BY_ID_INPUT_WITH_NAME_BLANK = () ->
        new UpdateClientByIdInput("    ", LocalDate.of(1990, 1, 1), "1234567890", "123 Main St");

    protected static final Supplier<UpdateClientByIdService.UpdateClientByIdInput> UPDATE_CLIENT_BY_ID_INPUT_ADDRESS_BLANK = () ->
        new UpdateClientByIdInput("John Doe", LocalDate.of(1990, 1, 1), "1234567890", "    ");

    protected static final Supplier<UpdateClientByIdService.UpdateClientByIdInput> UPDATE_CLIENT_BY_ID_INPUT_WITH_PHONE_BLANK = () ->
        new UpdateClientByIdInput("John Doe", LocalDate.of(1990, 1, 1), "    ", "123 Main St");

    protected static final Supplier<CreateClientService.CreateClientInput> CREATE_CLIENT_INPUT_VALID = () ->
        new CreateClientInput("123.456.789-00", "John Doe", LocalDate.of(1990, 1, 1), "1234567890", "123 Main St");

    protected static final Supplier<CreateClientService.CreateClientInput> CREATE_CLIENT_INPUT_NULL = () -> null;

    protected static final Supplier<CreateClientService.CreateClientInput> CREATE_CLIENT_INPUT_WITH_NAME_NULL = () ->
        new CreateClientInput("123.456.789-54", null, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St");

    protected static final Supplier<CreateClientService.CreateClientInput> CREATE_CLIENT_INPUT_WITH_DOCUMENT_NULL = () ->
        new CreateClientInput(null, "John Doe", LocalDate.of(1990, 1, 1), "1234567890", "123 Main St");

    protected static final Supplier<CreateClientService.CreateClientInput> CREATE_CLIENT_INPUT_WITH_DOCUMENT_INVALID = () ->
        new CreateClientInput("123.456.789", "John Doe", LocalDate.of(1990, 1, 1), "1234567890", "123 Main St");

    protected static final Supplier<CreateClientService.CreateClientInput> CREATE_CLIENT_INPUT_WITH_ADDRESS_NULL = () ->
        new CreateClientInput("123.456.789-00", "John Doe", LocalDate.of(1990, 1, 1), "1234567890", null);

    protected static final Supplier<CreateClientService.CreateClientInput> CREATE_CLIENT_INPUT_WITH_DATE_OF_BIRTH_NULL = () ->
        new CreateClientInput("123.456.789-00", "John Doe", null, "1234567890", "123 Main St");

    protected static final Supplier<CreateClientService.CreateClientInput> CREATE_CLIENT_INPUT_WITH_PHONE_NULL = () ->
        new CreateClientInput("123.456.789-00", "John Doe", LocalDate.of(1990, 1, 1), null, "123 Main St");

    protected static final Supplier<CreateClientService.CreateClientInput> CREATE_CLIENT_INPUT_WITH_NAME_BLANK = () ->
        new CreateClientInput("123.456.789-00", "    ", LocalDate.of(1990, 1, 1), "1234567890", "123 Main St");

    protected static final Supplier<CreateClientService.CreateClientInput> CREATE_CLIENT_INPUT_WITH_DOCUMENT_BLANK = () ->
        new CreateClientInput("   ", "John Doe", LocalDate.of(1990, 1, 1), "1234567890", "123 Main St");


    protected static final Supplier<CreateClientService.CreateClientInput> CREATE_CLIENT_INPUT_WITH_ADDRESS_BLANK = () ->
        new CreateClientInput("123.456.789-00", "John Doe", LocalDate.of(1990, 1, 1), "1234567890", "    ");


    protected static final Supplier<CreateClientService.CreateClientInput> CREATE_CLIENT_INPUT_WITH_PHONE_BLANK = () ->
        new CreateClientInput("123.456.789-00", "John Doe", LocalDate.of(1990, 1, 1), "    ", "123 Main St");

    protected static final Supplier<ClientModel> CLIENT_MODEL_VALID_WITH_ID = () -> {
        ClientModel client = new ClientModel();
        client.setId(UUID.randomUUID().toString());
        client.setDocument("12043678900");
        client.setName("John Doe 123");
        client.setDateOfBirth(LocalDate.of(1993, 5, 4));
        client.setPhone("12335570000678");
        client.setAddress("123 Main St 2244554");
        client.setCreatedAt(LocalDateTime.now());
        return client;
    };

    protected static final Supplier<String> ANY_STRING_ID_NULL = () -> null;
    protected static final Supplier<String> ANY_STRING_ID_BLANK = () -> "        ";
    protected static final Supplier<String> ANY_STRING_ID_VALID = () -> UUID.randomUUID().toString();

    protected static final Supplier<GetClientByIdOutput> GET_CLIENT_BY_ID_OUTPUT_VALID = () -> {
        return new GetClientByIdOutput(
            UUID.randomUUID().toString(),
            "12043678900",
            "John Doe 123",
            LocalDate.of(1993, 5, 4),
            "12335570000678",
            "123 Main St 2244554",
            LocalDateTime.now()
        );
    };

    
    
}
