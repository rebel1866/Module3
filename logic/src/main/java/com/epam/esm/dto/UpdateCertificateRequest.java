package com.epam.esm.dto;

import javax.validation.constraints.Pattern;

public class UpdateCertificateRequest {
    @Pattern(regexp = "[A-Za-zА-Яа-я ]+", message = "rCode15")
    private String certificateName;
    @Pattern(regexp = "[A-Za-zА-Яа-я ]+", message = "rCode16")
    private String description;
    @Pattern(regexp = "^[1-9]+[0-9]*$", message = "rCode12")
    private String price;
    @Pattern(regexp = "^[1-9]+[0-9]*$", message = "rCode1")
    private String duration;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
