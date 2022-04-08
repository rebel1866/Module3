package com.epam.esm.logic.impl;

import com.epam.esm.dto.SearchTagRequest;
import com.epam.esm.converter.TagEntityToDtoConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DaoException;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.LogicException;
import com.epam.esm.logic.TagLogic;
import com.epam.esm.converter.ObjectToMapConverter;
import com.google.common.base.CaseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagLogicImpl implements TagLogic {
    private TagDao tagDao;

    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<TagDto> findTags(SearchTagRequest request) {
        Map<String, String> params = ObjectToMapConverter.convertToMap(request);
        var iterator = params.entrySet().iterator();
        Map<String, String> newParams = new HashMap<>();
        while (iterator.hasNext()) {
            var el = iterator.next();
            String key = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, el.getKey());
            String value = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, String.valueOf(el.getValue()));
            newParams.put(key, value);
        }
        List<Tag> tags;
        tags = tagDao.findTags(newParams);
        return TagEntityToDtoConverter.convertList(tags);
    }

    @Override
    public TagDto findTagById(int id) {
        validateId(id);
        Tag tag;
        tag = tagDao.findTagById(id);
        return TagEntityToDtoConverter.convert(tag);
    }

    @Override
    @Transactional
    public TagDto addTag(String tagName) {
        Tag tag = new Tag(tagName);
        Tag addedTag;
        addedTag = tagDao.addTag(tag);
        return TagEntityToDtoConverter.convert(addedTag);
    }

    @Override
    public void deleteTag(int id) {
        validateId(id);
        tagDao.deleteTag(id);
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new LogicException("messageCode10", "errorCode=3");
        }
    }
}
