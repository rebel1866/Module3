package com.epam.esm.dto;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class TagDto {
    @Positive(message = "messageCode10")
    private Integer tagId;
    @Pattern(regexp = "[A-Za-zА-Яа-я ]+", message = "rCode17")
    private String tagName;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
