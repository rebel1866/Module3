package com.epam.esm.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;


public class SearchCertificateRequest {
    private String certificateName;
    private String tagName;
    @Positive(message = "rCode2")
    private Integer priceFrom;
    @Positive(message = "rCode3")
    private Integer priceTo;
    @Pattern(regexp = "price|certificate_name|creation_date|certificate_name, creation_date",
            message = "rCode4")
    private String sorting;
    @Pattern(regexp = "asc|desc", message = "rCode5")
    private String sortingOrder;

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
    }

    public String getSorting() {
        return sorting;
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }

    public String getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(String sortingOrder) {
        this.sortingOrder = sortingOrder;
    }
}
