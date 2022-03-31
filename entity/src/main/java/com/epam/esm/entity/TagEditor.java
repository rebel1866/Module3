package com.epam.esm.entity;


import java.beans.PropertyEditorSupport;

public class TagEditor extends PropertyEditorSupport {
    @Override
    public String getAsText() {
        Tag tag = (Tag) getValue();
        return tag == null ? "" : tag.getTagName();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
            Tag tag = new Tag();
            tag.setTagId(Integer.parseInt(text));
            setValue(tag);
    }
}
