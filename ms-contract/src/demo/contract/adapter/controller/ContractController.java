package demo.contract.adapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.contract.application.service.CreateContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/contracts")
@Tag(name = "Contract operations")
public class ContractController {

    private final CreateContractService createContractService;

    public ContractController(CreateContractService createContractService) {
        this.createContractService = createContractService;
    }

    @Operation(
        summary = "Save a new contract based on a previous quote simulation",
        responses = {
            @ApiResponse(
                responseCode = "201", 
                content = {@Content(schema = @Schema(implementation = CreateContractService.CreateContractOutput.class))}
            )
        }
    )
    @PostMapping
    public ResponseEntity<CreateContractService.CreateContractOutput> create(@RequestBody(required = false) CreateContractService.CreateContractInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createContractService.create(input));
    }
    
}
