package com.epam.esm;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.epam.esm.exception.DaoException;
import com.epam.esm.testconfig.DaoTestConfig;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
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
                new ClassPathResource("scripts.create/create_db.sql"));
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("scripts.create/insert_tag.sql"));
    }


    @Test
    public void addAndSearchCertificateTest() throws DaoException {
        Certificate certificate = getCertificate();
        certificateDao.addCertificate(certificate);
        Map<String, String> params = new HashMap<>();
        params.put("certificate_name", "TEST");
        List<Certificate> certificates = certificateDao.findCertificates(params);
        Assertions.assertEquals(certificate.getCertificateName(), certificates.get(0).getCertificateName());
    }

    @Test
    public void deleteCertificateTest() throws DaoException {
        Certificate certificate = getCertificate();
        certificateDao.addCertificate(certificate);
        Map<String, String> params = new HashMap<>();
        params.put("certificate_name", "TEST");
        List<Certificate> certificates = certificateDao.findCertificates(params);
        Assertions.assertEquals("TEST", certificates.get(0).getCertificateName());
        int id = certificates.get(0).getGiftCertificateId();
        certificateDao.deleteCertificate(id);
        DaoException thrown = Assertions.assertThrows(DaoException.class, () -> certificateDao.findCertificates(params));
        Assertions.assertEquals("messageCode1", thrown.getMessage());
    }

    @Test
    public void updateCertificateTest() throws DaoException {
        Certificate certificate = getCertificate();
        certificateDao.addCertificate(certificate);
        Map<String, String> params = new HashMap<>();
        params.put("certificate_name", "TEST");
        List<Certificate> certificates = certificateDao.findCertificates(params);
        Assertions.assertEquals("TEST", certificates.get(0).getCertificateName());
        int id = certificates.get(0).getGiftCertificateId();
        Map<String, String> updateParams = new HashMap<>();
        updateParams.put("certificate_name", "NEW_TEST");
        certificateDao.updateCertificate(updateParams, id, new ArrayList<>());
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