package com.epam.esm.logic;

import com.epam.esm.converter.SearchTagRequest;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.LogicException;

import java.util.List;

public interface TagLogic {
    List<TagDto> findTags(SearchTagRequest request) throws LogicException;

    TagDto addTag(String tagName) throws LogicException;

    void deleteTag(int id) throws LogicException;

    TagDto findTagById(int id) throws LogicException;
}
