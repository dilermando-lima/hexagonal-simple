package demo.contract.application.model;

import java.time.LocalDateTime;

public class QuoteModel {

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
