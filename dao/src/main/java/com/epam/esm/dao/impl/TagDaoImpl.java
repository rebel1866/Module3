package com.epam.esm.dao.impl;

import com.epam.esm.exception.DaoException;
import com.epam.esm.dao.TagDao;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.sqlgenerator.SqlGenerator;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class TagDaoImpl implements TagDao {
    private JdbcTemplate jdbcTemplate;
    private static final String tagSQL = "select tag_name, tag_id from gifts.tags";
    private static final String addTagSql = "insert into gifts.tags (tag_name) values (?)";
    private static final String deleteTagSql = "delete from gifts.tags where tag_id =?";
    private static final String findByIdSql = "select * from gifts.tags where tag_id =?";
    private static final String lastIdSql = "select max(tag_id) as max from gifts.tags";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findTags(Map<String, String> params) throws DaoException {
        String targetSql = SqlGenerator.generateSQL(tagSQL, params);
        List<Tag> tags = jdbcTemplate.query(targetSql, new TagMapper());
        if (tags.size() == 0) {
            throw new DaoException("messageCode6", "errorCode=1");
        }
        return tags;
    }

    @Override
    public Tag findTagById(int id) throws DaoException {
        Tag tag;
        try {
            tag = jdbcTemplate.queryForObject(findByIdSql, new TagMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new DaoException("messageCode7", "errorCode=1");
        }
        return tag;
    }

    @Override
    public Tag addTag(Tag tag) throws DaoException {
        int rowsAffected = jdbcTemplate.update(addTagSql, tag.getTagName());
        if (rowsAffected == 0) {
            throw new DaoException("messageCode8", "errorCode=2");
        }
        int id = getLastId();
        return findTagById(id);
    }

    @Override
    public void deleteTag(int id) throws DaoException {
        int rowsAffected = jdbcTemplate.update(deleteTagSql, id);
        if (rowsAffected == 0) {
            throw new DaoException("messageCode9", "errorCode=2");
        }
    }

    private int getLastId() {
        Integer value = jdbcTemplate.queryForObject(lastIdSql, Integer.class);
        return Objects.requireNonNullElse(value, 0);
    }
}
