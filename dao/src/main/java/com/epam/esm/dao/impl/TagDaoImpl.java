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
    private static final String TAG_SQL = "select tag_name, tag_id from gifts.tags";
    private static final String ADD_TAG_SQL = "insert into gifts.tags (tag_name) values (?)";
    private static final String DELETE_TAG_SQL = "delete from gifts.tags where tag_id =?";
    private static final String FIND_BY_ID_SQL = "select * from gifts.tags where tag_id =?";
    private static final String LAST_ID_SQL = "select max(tag_id) as max from gifts.tags";
    private static final String SEARCH_BY_NAME_SQL = "select tag_name, tag_id from gifts.tags where tag_name=?";
    private static final String ADD_TAGS_OF_CERTIFICATE = "insert into gifts.cert_tags (gift_certificate_id, tag_id) " +
            "values (?,?)";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findTags(Map<String, String> params) {
        String targetSql = SqlGenerator.generateSQL(TAG_SQL, params);
        List<Tag> tags = jdbcTemplate.query(targetSql, new TagMapper());
        if (tags.size() == 0) {
            throw new DaoException("messageCode6", "errorCode=1");
        }
        return tags;
    }

    @Override
    public Tag findTagById(int id) {
        Tag tag;
        try {
            tag = jdbcTemplate.queryForObject(FIND_BY_ID_SQL, new TagMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new DaoException("WmessageCode7:" + id, "errorCode=1");
        }
        return tag;
    }

    @Override
    public Tag addTag(Tag tag) {
        if (isTagExist(tag)) {
            throw new DaoException("WmessageCode12:" + tag.getTagName(), "errorCode=3");
        }
        int rowsAffected = jdbcTemplate.update(ADD_TAG_SQL, tag.getTagName());
        if (rowsAffected == 0) {
            throw new DaoException("messageCode8", "errorCode=2");
        }
        int id = getLastId();
        return findTagById(id);
    }

    @Override
    public void deleteTag(int id) {
        int rowsAffected = jdbcTemplate.update(DELETE_TAG_SQL, id);
        if (rowsAffected == 0) {
            throw new DaoException("messageCode9", "errorCode=2");
        }
    }

    @Override
    public void addTagToCertificate(Tag tag, int certificateId) {
        jdbcTemplate.update(ADD_TAGS_OF_CERTIFICATE, certificateId, tag.getTagId());
    }

    private int getLastId() {
        Integer value = jdbcTemplate.queryForObject(LAST_ID_SQL, Integer.class);
        return Objects.requireNonNullElse(value, 0);
    }

    public boolean isTagExist(Tag tag) {
        List<Tag> tags = jdbcTemplate.query(SEARCH_BY_NAME_SQL, new TagMapper(), tag.getTagName());
        return tags.size() > 0;
    }
}
