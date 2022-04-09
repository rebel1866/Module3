package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.DaoException;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.sqlgenerator.SqlGenerator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class CertificateDaoImpl implements CertificateDao {
    private JdbcTemplate jdbcTemplate;
    private TagDao tagDao;
    private static final String certificatesSQL = "select f.gift_certificate_id, certificate_name, price, duration, " +
            "creation_date, last_update_time, description from gifts.gift_certificates as f inner join gifts.cert_tags as s " +
            "on s.gift_certificate_id = f.gift_certificate_id inner join gifts.tags as t on t.tag_id = s.tag_id";
    private static final String findTagsByIdSQL = "select t.tag_id, tag_name from gifts.gift_certificates as f inner join " +
            "gifts.cert_tags as s on f.gift_certificate_id = s.gift_certificate_id inner join gifts.tags as t on t.tag_id = " +
            "s.tag_id where f.gift_certificate_id =?";
    private static final String addCertificateSQL = "insert into gifts.gift_certificates (certificate_name, description, price, " +
            "duration, creation_date, last_update_time) values (?,?,?,?,?,?)";
    private static final String addCertificateTagsSQL = "insert into gifts.cert_tags (gift_certificate_id, tag_id) " +
            "values ((select gift_certificate_id from gifts.gift_certificates order by gift_certificate_id desc limit 1),?)";
    private static final String removeCertificateSql = "delete from gifts.gift_certificates where gift_certificate_id = ?";
    private static final String updateSql = "update gifts.gift_certificates set where gift_certificate_id=?";
    private static final String findByIdSql = "select * from gifts.gift_certificates where gift_certificate_id =?";
    private static final String lastIdSql = "select max(gift_certificate_id) as max from gifts.gift_certificates";
    private static final String TAG_BY_ID = "select * from gifts.tags where tag_id=?";
    private static final String ADD_TAGS_OF_CERTIFICATE = "insert into gifts.cert_tags (gift_certificate_id, tag_id) " +
            "values (?,?)";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Certificate> findCertificates(Map<String, String> params) {
        String targetSql = SqlGenerator.generateSQL(certificatesSQL, params);
        List<Certificate> certificates = jdbcTemplate.query(targetSql, new CertificateMapper());
        certificates = certificates.stream().distinct().collect(Collectors.toList());
        for (Certificate certificate : certificates) {
            int certificateId = certificate.getGiftCertificateId();
            List<Tag> tags = jdbcTemplate.query(findTagsByIdSQL, new TagMapper(), certificateId);
            certificate.setTags(tags);
        }
        if (certificates.size() == 0) {
            throw new DaoException("messageCode1", "errorCode=1");
        }
        return certificates;
    }


    @Override
    public Certificate findCertificateById(int id) {
        Certificate certificate;
        try {
            certificate = jdbcTemplate.queryForObject(findByIdSql, new CertificateMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new DaoException("messageCode2", "errorCode=1");
        }
        List<Tag> tags = jdbcTemplate.query(findTagsByIdSQL, new TagMapper(), id);
        certificate.setTags(tags);
        return certificate;
    }

    @Override
    public Certificate addCertificate(Certificate certificate) {
        int rowAffected = jdbcTemplate.update(addCertificateSQL, certificate.getCertificateName(),
                certificate.getDescription(), certificate.getPrice(), certificate.getDuration(),
                certificate.getCreationDate(), certificate.getLastUpdateTime());
        if (rowAffected == 0) {
            throw new DaoException("messageCode3", "errorCode=2");
        }
        List<Tag> tags = certificate.getTags();
        for (Tag tag : tags) {
            int id = tag.getTagId();
            try {
                jdbcTemplate.queryForObject(TAG_BY_ID, new TagMapper(), id);
            } catch (EmptyResultDataAccessException e) {
                throw new DaoException("messageCode14", "errorCode=2");
            }
            jdbcTemplate.update(addCertificateTagsSQL, id);
        }
        int id = getLastId();
        return findCertificateById(id);
    }

    @Override
    public void deleteCertificate(int id) {
        int rowAffected = jdbcTemplate.update(removeCertificateSql, id);
        if (rowAffected == 0) {
            throw new DaoException("messageCode4", "errorCode=3");
        }
    }

    @Override
    public Certificate updateCertificate(Map<String, String> params, int id, List<Tag> tags) {
        String targetSql = SqlGenerator.generateUpdateSql(params, updateSql);
        int rowAffected = jdbcTemplate.update(targetSql, id);
        if (rowAffected == 0) {
            throw new DaoException("messageCode5", "errorCode=3");
        }
        if (tags.size() != 0) {
            List<Tag> tagsWithId = tags.stream().map(tag -> tagDao.addTag(tag)).collect(Collectors.toList());
            tagsWithId.forEach(tag -> jdbcTemplate.update(ADD_TAGS_OF_CERTIFICATE, id, tag.getTagId()));
        }
        return findCertificateById(id);
    }

    private int getLastId() {
        Integer value = jdbcTemplate.queryForObject(lastIdSql, Integer.class);
        return Objects.requireNonNullElse(value, 0);
    }
}
