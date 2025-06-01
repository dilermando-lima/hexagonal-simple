package demo.contract.adapter.repository;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import demo.contract.application.model.ClientModel;
import demo.contract.application.model.PolicyModel;

@Document(collection = "QUOTE")
public class QuoteDocument {

    @Id
    private String id;
    private ClientModel client;
    private PolicyModel policy;
    private LocalDateTime createdAt;
    private LocalDateTime expiresIn;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public ClientModel getClient() {
        return client;
    }
    public void setClient(ClientModel client) {
        this.client = client;
    }
    public PolicyModel getPolicy() {
        return policy;
    }
    public void setPolicy(PolicyModel policy) {
        this.policy = policy;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(LocalDateTime expiresIn) {
        this.expiresIn = expiresIn;
    }

    
    
}
