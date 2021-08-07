package com.lucci.webadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.PricingContent} entity.
 */
public class PricingContentDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String content;

    @NotNull
    private Boolean pro;


    private Long pricingCardId;

    private String pricingCardName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isPro() {
        return pro;
    }

    public void setPro(Boolean pro) {
        this.pro = pro;
    }

    public Long getPricingCardId() {
        return pricingCardId;
    }

    public void setPricingCardId(Long pricingCardId) {
        this.pricingCardId = pricingCardId;
    }

    public String getPricingCardName() {
        return pricingCardName;
    }

    public void setPricingCardName(String pricingCardName) {
        this.pricingCardName = pricingCardName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PricingContentDTO)) {
            return false;
        }

        return id != null && id.equals(((PricingContentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PricingContentDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", pro='" + isPro() + "'" +
            ", pricingCardId=" + getPricingCardId() +
            ", pricingCardName='" + getPricingCardName() + "'" +
            "}";
    }
}
