package com.epam.esm.logic;

import com.epam.esm.dto.AddTagRequest;
import com.epam.esm.dto.SearchTagRequest;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.LogicException;

import java.util.List;

public interface TagLogic {
    List<Tag> findTags(SearchTagRequest request) throws LogicException;

    void addTag(AddTagRequest request) throws LogicException;

    void deleteTag(int id) throws LogicException;
}
