package demo.customer.adapter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.customer.application.service.CreateClientService;
import demo.customer.application.service.DeleteClientByIdService;
import demo.customer.application.service.GetClientByIdService;
import demo.customer.application.service.ListClientService;
import demo.customer.application.service.UpdateClientByIdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clients")
@Tag(name = "Client operations")
public class ClientController {


    private final CreateClientService createService;
    private final ListClientService listService;
    private final GetClientByIdService getByIdService;
    private final UpdateClientByIdService updateByIdService;
    private final DeleteClientByIdService deleteByIdService;

    
    public ClientController(
        CreateClientService createService, 
        ListClientService listService, 
        GetClientByIdService getByIdService, 
        UpdateClientByIdService updateByIdService, 
        DeleteClientByIdService deleteByIdService
    ) {
        this.createService = createService;
        this.listService = listService;
        this.getByIdService = getByIdService;
        this.updateByIdService = updateByIdService;
        this.deleteByIdService = deleteByIdService;
    }


    @Operation(
        summary = "Create a new client",
        responses = {
            @ApiResponse(
                responseCode = "201", 
                content = {@Content(schema = @Schema(implementation = CreateClientService.CreateClientOutput.class))}
            )
        }
    )
    @PostMapping
    public ResponseEntity<CreateClientService.CreateClientOutput> create(@RequestBody(required = false) CreateClientService.CreateClientInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createService.insert(input));
    }

    public record RequestList(
        @RequestParam(required = false) String nameLK,
        @RequestParam(required = false) String addressLK,
        @RequestParam(required = false) String phoneEQ,
        @RequestParam(required = false) String documentEQ
    ) {}
    
    @Operation(
        summary = "List client by filter",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ListClientService.ClientOutputItem.class)))}
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<ListClientService.ClientOutputItem>> list(
        @RequestParam(required = false) String nameLK,
        @RequestParam(required = false) String addressLK,
        @RequestParam(required = false) String phoneEQ,
        @RequestParam(required = false) String documentEQ
    ) {
        return ResponseEntity.ok(listService.list(nameLK, addressLK, phoneEQ, documentEQ));
    }


    @Operation(
        summary = "Update a client by id",
        responses = {@ApiResponse(responseCode = "204")}
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable(name = "id") String id, @RequestBody(required = false) UpdateClientByIdService.UpdateClientByIdInput input) {
        updateByIdService.update(id, input);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Detail a client by id",
        responses = {
                @ApiResponse(
                    responseCode = "200", 
                    content = {@Content(schema = @Schema(implementation = GetClientByIdService.GetClientByIdOutput.class))}
                )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<GetClientByIdService.GetClientByIdOutput> getById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(getByIdService.getById(id));
    }

    @Operation(
        summary = "Delete a client by id",
        responses = {@ApiResponse(responseCode = "204")}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") String id) {
        deleteByIdService.delete(id);
        return ResponseEntity.noContent().build();
    }

    
}
