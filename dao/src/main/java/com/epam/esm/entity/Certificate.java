package com.epam.esm.entity;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Certificate implements Serializable {
    private static final long SerialVersionUID = 5545654654564504L;
    private Integer giftCertificateId;
    private String certificateName;
    private String description;
    private Integer price;
    private Integer duration;
    private List<Tag> tags;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateTime;

    public Certificate() {

    }

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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return Objects.equals(giftCertificateId, that.giftCertificateId) && Objects.equals(certificateName, that.certificateName) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(duration, that.duration) && Objects.equals(tags, that.tags) && Objects.equals(creationDate, that.creationDate) && Objects.equals(lastUpdateTime, that.lastUpdateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giftCertificateId, certificateName, description, price, duration, tags, creationDate, lastUpdateTime);
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "giftCertificateId=" + giftCertificateId +
                ", certificateName='" + certificateName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", tags=" + tags +
                ", creationDate=" + creationDate +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
