package demo.contract.application.model;

import java.math.BigDecimal;

public class CoverageModel {

    private String title;
    private String detail;
    private BigDecimal premium;

    public CoverageModel() {
    }
    
    public CoverageModel(String title, String detail, BigDecimal premium) {
        this.title = title;
        this.detail = detail;
        this.premium = premium;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public BigDecimal getPremium() {
        return premium;
    }
    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }
}
