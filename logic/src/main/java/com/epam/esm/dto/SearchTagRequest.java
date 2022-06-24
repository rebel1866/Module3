package com.epam.esm.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SearchTagRequest {
    @Size(min = 1,message = "rCode17")
    private String tagName;
    @Pattern(regexp = "tagId|tagName",
            message = "rCode18")
    private String sorting;
    @Pattern(regexp = "rCode5")
    private String sortingOrder;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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
