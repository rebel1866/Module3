package com.epam.esm.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SearchTagRequest {
    @Size(min = 1,message = "rCode17")
    private String tagName;
    @Pattern(regexp = "tagId|tagName",
            message = "Acceptable values for field \"sorting\" are: 1)tagName 2)tagId")
    private String sorting;
    @Pattern(regexp = "asc|desc", message = "sortingOrder must be: \"asc\" or \"desc\"")
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
