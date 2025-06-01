package demo.contract.application.model;

import java.time.LocalDate;

public class ClientModel {

    private String registryId;
    private String document;
    private String name;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;

    public String getRegistryId() {
        return registryId;
    }
    public void setRegistryId(String registryId) {
        this.registryId = registryId;
    }
    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    

}
