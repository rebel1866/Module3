package com.epam.esm.logic.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;


public class SearchCertificateRequest {
    private String certificateName;
    private String tagName;
    @Positive(message = "Field priceFrom must be positive number")
    private Integer priceFrom;
    @Positive(message = "Field priceTo must be positive number")
    private Integer priceTo;
    @Pattern(regexp = "order by price|order by certificate_name|order by creation_date|order by certificate_name, creation_date",
            message = "Acceptable values for field \"sorting\" are: 1)order by price 2)order by certificate_name " +
                    "3)order by creation_date 4)order by certificate_name, creation_date")
    private String sorting;
    @Pattern(regexp = "asc|desc", message = "sorting_order must be: \"asc\" or \"desc\"")
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
