package com.epam.esm.dao.impl;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.epam.esm.dao.exceptions.DaoException;
import com.epam.esm.dao.impl.testconfig.DaoTestConfig;
import com.epam.esm.dao.interfaces.CertificateDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoTestConfig.class)
@WebAppConfiguration
public class CertificateDaoImplTest {

    @Autowired
    private CertificateDao certificateDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    public void before() throws SQLException {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("scripts.create/certificates_c.sql"));
    }


    @Test
    public void testAddAndSearch() throws DaoException {
        Certificate certificate = getCertificate();
        certificateDao.addCertificate(certificate);
        Map<String, String> params = new HashMap<>();
        params.put("certificate_name", "TEST");
        List<Certificate> certificates = certificateDao.findCertificates(params);
        Assertions.assertEquals(certificate.getCertificateName(), certificates.get(0).getCertificateName());
    }

    @Test
    public void testDelete() throws DaoException {
        Certificate certificate = getCertificate();
        certificateDao.addCertificate(certificate);
        Map<String, String> params = new HashMap<>();
        params.put("certificate_name", "TEST");
        List<Certificate> certificates = certificateDao.findCertificates(params);
        Assertions.assertEquals("TEST", certificates.get(0).getCertificateName());
        int id = certificates.get(0).getGiftCertificateId();
        certificateDao.deleteCertificate(id);
        DaoException thrown = Assertions.assertThrows(DaoException.class, () -> certificateDao.findCertificates(params));
        Assertions.assertEquals("No certificates found", thrown.getMessage());
    }

    @Test
    public void testUpdate() throws DaoException {
        Certificate certificate = getCertificate();
        certificateDao.addCertificate(certificate);
        Map<String, String> params = new HashMap<>();
        params.put("certificate_name", "TEST");
        List<Certificate> certificates = certificateDao.findCertificates(params);
        Assertions.assertEquals("TEST", certificates.get(0).getCertificateName());
        int id = certificates.get(0).getGiftCertificateId();
        Map<String, String> updateParams = new HashMap<>();
        updateParams.put("giftCertificateId", String.valueOf(id));
        updateParams.put("certificate_name", "NEW_TEST");
        certificateDao.updateCertificate(updateParams);
        Certificate newCertificate = certificateDao.findCertificates(new HashMap<>()).get(0);
        Assertions.assertEquals("NEW_TEST", newCertificate.getCertificateName());
    }

    @AfterEach
    public void after() {
        JdbcTestUtils.dropTables(jdbcTemplate, "gifts.cert_tags", "gifts.tags", "gifts.gift_certificates");
    }

    public static Certificate getCertificate() {
        Certificate certificate = new Certificate();
        certificate.setCertificateName("TEST");
        certificate.setLastUpdateTime(LocalDateTime.now());
        certificate.setCreationDate(LocalDateTime.now());
        Tag tag = new Tag();
        tag.setTagId(1);
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        certificate.setTags(tags);
        return certificate;
    }
}