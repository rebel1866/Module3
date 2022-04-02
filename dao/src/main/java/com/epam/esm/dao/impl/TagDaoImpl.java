package com.epam.esm.dao.impl;

import com.epam.esm.dao.exceptions.DaoException;
import com.epam.esm.dao.interfaces.TagDao;
import com.epam.esm.dao.mappers.TagMapper;
import com.epam.esm.dao.sqlgenerator.SqlGenerator;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TagDaoImpl implements TagDao {
    private JdbcTemplate jdbcTemplate;
    private static final String tagSQL = "select tag_name, tag_id from tags";
    private static final String addTagSql = "insert into gifts.tags (tag_name) values (?)";
    private static final String deleteTagSql = "delete from gifts.tags where tag_id =?";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findTags(Map<String, String> params) throws DaoException {
        String targetSql = SqlGenerator.generateSQL(tagSQL, params);
        List<Tag> tags = jdbcTemplate.query(targetSql, new TagMapper());
        if (tags.size() == 0) throw new DaoException("No tags found", "errorCode=1");
        return tags;
    }

    @Override
    public void addTag(Tag tag) throws DaoException {
        int rowsAffected = jdbcTemplate.update(addTagSql, tag.getTagName());
        if (rowsAffected == 0) throw new DaoException("Tag has not been added", "errorCode=2");
    }

    @Override
    public void deleteTag(int id) throws DaoException {
        int rowsAffected = jdbcTemplate.update(deleteTagSql, id);
        if (rowsAffected == 0) throw new DaoException("Tag has not been deleted", "errorCode=2");
    }
}
