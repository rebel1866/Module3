package com.epam.esm.dao;

import com.epam.esm.exception.DaoException;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Map;

public interface TagDao {
    List<Tag> findTags(Map<String,String> params) throws DaoException;

    Tag addTag(Tag tag) throws DaoException;

    void deleteTag(int id) throws DaoException;

    Tag findTagById(int id) throws DaoException;
    void addTagToCertificate(Tag tag, int certificateId);
}
