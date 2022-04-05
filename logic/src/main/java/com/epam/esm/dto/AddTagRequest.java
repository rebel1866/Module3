package com.epam.esm.dto;

import javax.validation.constraints.NotBlank;

public class AddTagRequest {
    @NotBlank(message = "fill in tagName field")
    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
