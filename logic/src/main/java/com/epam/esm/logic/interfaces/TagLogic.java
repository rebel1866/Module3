package com.epam.esm.logic.interfaces;

import com.epam.esm.logic.dto.AddTagRequest;
import com.epam.esm.logic.dto.DeleteByIdRequest;
import com.epam.esm.logic.dto.SearchTagRequest;
import com.epam.esm.entity.Tag;
import com.epam.esm.logic.exceptions.LogicException;

import java.util.List;

public interface TagLogic {
    List<Tag> findTags(SearchTagRequest request) throws LogicException;

    void addTag(AddTagRequest request) throws LogicException;

    void deleteTag(DeleteByIdRequest request) throws LogicException;
}
