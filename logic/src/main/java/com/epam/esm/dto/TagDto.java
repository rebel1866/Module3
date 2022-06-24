package com.epam.esm.dto;


import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TagDto {
    @Positive(message = "messageCode10")
    private Integer tagId;
    @Size(min = 1,message = "rCode17")
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

    @Override
    public String toString() {
        return "TagDto{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
