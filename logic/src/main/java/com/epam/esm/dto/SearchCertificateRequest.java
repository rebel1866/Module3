package com.epam.esm.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;


public class SearchCertificateRequest {
    @Pattern(regexp = "[A-Za-zА-Яа-я]+",message = "rCode13")
    private String certificateName;
    @Pattern(regexp = "[A-Za-zА-Яа-я]+",message = "rCode14")
    private String tagName;
    @Pattern(regexp = "^[1-9]+[0-9]*$",message = "rCode2")
    private String priceFrom;
    @Pattern(regexp = "^[1-9]+[0-9]*$",message = "rCode3")
    private String priceTo;
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

    public String getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(String priceFrom) {
        this.priceFrom = priceFrom;
    }

    public String getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(String priceTo) {
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
