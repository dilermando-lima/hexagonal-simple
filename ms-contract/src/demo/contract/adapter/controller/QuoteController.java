package demo.contract.adapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.contract.application.service.CreateQuoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/quotes")
@Tag(name = "Quote operations")
public class QuoteController {

    private final CreateQuoteService createQuoteService;

    public QuoteController(CreateQuoteService createQuoteService) {
        this.createQuoteService = createQuoteService;
    }

    @Operation(
        summary = "Save a new quote simulation",
        responses = {
            @ApiResponse(
                responseCode = "201", 
                content = {@Content(schema = @Schema(implementation = CreateQuoteService.CreateQuoteOutput.class))}
            )
        }
    )
    @PostMapping
    public ResponseEntity<CreateQuoteService.CreateQuoteOutput> create(@RequestBody(required = false) CreateQuoteService.CreateQuoteInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createQuoteService.create(input));
    }

}
