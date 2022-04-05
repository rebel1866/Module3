package com.epam.esm.dao.interfaces;

import com.epam.esm.dao.exceptions.DaoException;
import com.epam.esm.dao.entity.Tag;

import java.util.List;
import java.util.Map;

public interface TagDao {
    List<Tag> findTags(Map<String,String> params) throws DaoException;

    void addTag(Tag tag) throws DaoException;

    void deleteTag(int id) throws DaoException;
}
