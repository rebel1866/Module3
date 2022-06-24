package com.epam.esm.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UpdateCertificateRequest {
    @Size(min = 1,message = "rCode15")
    private String certificateName;
    @Size(min = 1,message = "rCode16")
    private String description;
    @Pattern(regexp = "^[1-9]+[0-9]*$", message = "rCode12")
    private String price;
    @Pattern(regexp = "^[1-9]+[0-9]*$", message = "rCode1")
    private String duration;
    @Valid
    private List<TagDto> tags;

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

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }
}
