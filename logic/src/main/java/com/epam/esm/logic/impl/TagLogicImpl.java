package com.epam.esm.logic.impl;

import com.epam.esm.dto.AddTagRequest;
import com.epam.esm.dto.SearchTagRequest;
import com.epam.esm.exception.DaoException;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.LogicException;
import com.epam.esm.logic.TagLogic;
import com.epam.esm.converter.ObjectToMapConverter;
import com.google.common.base.CaseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Tag> findTags(SearchTagRequest request) throws LogicException {
        Map<String, String> params = ObjectToMapConverter.convertToMap(request);
        var iterator = params.entrySet().iterator();
        Map<String, String> newParams = new HashMap<>();
        while (iterator.hasNext()) {
            var el = iterator.next();
            String key = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, el.getKey());
            String value = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, String.valueOf(el.getValue()));
            newParams.put(key, value);
        }
        try {
            return tagDao.findTags(newParams);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e.getErrorCode(), e);
        }
    }
//findByID
    @Override
    public void addTag(AddTagRequest request) throws LogicException {
        Tag tag = new Tag();
        tag.setTagName(request.getTagName());//converter
        try {
            tagDao.addTag(tag);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e.getErrorCode(), e);
        }
    }

    @Override
    public void deleteTag(int id) throws LogicException {
        if (id <= 0) {
            throw new LogicException("Id must be positive integer number", "errorCode=3");
        }
        try {
            tagDao.deleteTag(id);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e.getErrorCode(), e);
        }
    }
}
