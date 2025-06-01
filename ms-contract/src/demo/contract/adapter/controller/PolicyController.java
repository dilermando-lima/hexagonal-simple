package demo.contract.adapter.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.contract.application.service.GetPolicyByIdService;
import demo.contract.application.service.ListPolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/policies")
@Tag(name = "Policy operations")
public class PolicyController {

    private final GetPolicyByIdService getPolicyByIdService;
    private final ListPolicyService listPolicyService;

    public PolicyController(GetPolicyByIdService getPolicyByIdService, ListPolicyService listPolicyService) {
        this.getPolicyByIdService = getPolicyByIdService;
        this.listPolicyService = listPolicyService;
    }

    @Operation(
        summary = "List all available policies",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ListPolicyService.OutputItem.class)))}
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<ListPolicyService.OutputItem>> listAll() {
        return ResponseEntity.ok(listPolicyService.listAll());
    }

    @Operation(
        summary = "Detail a policy by id",
        responses = {
                @ApiResponse(
                    responseCode = "200", 
                    content = {@Content(schema = @Schema(implementation = GetPolicyByIdService.GetByIdOutput.class))}
                )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<GetPolicyByIdService.GetByIdOutput> getById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(getPolicyByIdService.getById(id));
    }

    
}
