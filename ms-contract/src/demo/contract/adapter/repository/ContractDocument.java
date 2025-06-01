package demo.contract.adapter.repository;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import demo.contract.application.model.QuoteModel;

@Document(collection = "CONTRACT")
public class ContractDocument {

    @Id
    private String id;
    private QuoteModel quote;
    private LocalDateTime startedAt;
    private LocalDateTime expiresIn;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public QuoteModel getQuote() {
        return quote;
    }
    public void setQuote(QuoteModel quote) {
        this.quote = quote;
    }
    public LocalDateTime getStartedAt() {
        return startedAt;
    }
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(LocalDateTime expiresIn) {
        this.expiresIn = expiresIn;
    }

  
    
    
}
