package com.epam.esm.dto;

import javax.validation.constraints.Positive;

public class UpdateCertificateRequest {
    private String certificateName;
    private String description;
    @Positive
    private Integer price;
    @Positive(message = "duration must be positive number")
    private Integer duration;

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
