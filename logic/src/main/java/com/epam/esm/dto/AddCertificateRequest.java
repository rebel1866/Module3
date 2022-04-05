package com.epam.esm.dto;

import com.epam.esm.entity.Tag;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public class AddCertificateRequest {
    private Integer giftCertificateId;
    @NotBlank(message = "certificateName must be filled")
    private String certificateName;
    @NotBlank(message = "description must be filled")
    private String description;
    @Positive
    @NotNull(message = "price must be filled")
    private Integer price;
    @NotNull(message = "duration must be filled")
    @Positive(message = "duration must be positive number")
    private Integer duration;
    @Valid
    @NotNull(message = "tags must be filled")
    private List<Tag> tags;


    public Integer getGiftCertificateId() {
        return giftCertificateId;
    }

    public void setGiftCertificateId(Integer giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}