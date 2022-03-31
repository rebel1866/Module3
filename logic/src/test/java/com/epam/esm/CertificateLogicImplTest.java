package com.epam.esm;


import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.logic.dto.DeleteByIdRequest;
import com.epam.esm.logic.dto.SearchCertificateRequest;
import com.epam.esm.logic.dto.UpdateCertificateRequest;
import com.epam.esm.logic.exceptions.LogicException;
import com.epam.esm.logic.interfaces.CertificateLogic;
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
import org.springframework.test.jdbc.JdbcTestUtils;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@ExtendWith(SpringExtension.class)
class CertificateLogicImplTest {

    @Autowired
    private CertificateLogic certificateLogic;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    public void before() throws SQLException {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("scripts.create/certificates_c.sql"));
    }

    @Test
    public void testAdd() {
        Certificate certificate = getCertificate();
        assertDoesNotThrow(() -> certificateLogic.addCertificate(certificate));
    }

    @Test
    public void testFind() throws LogicException {
        Certificate certificate = getCertificate();
        certificateLogic.addCertificate(certificate);
        SearchCertificateRequest request = new SearchCertificateRequest();
        request.setCertificateName("TEST");
        List<Certificate> certificates = certificateLogic.findCertificates(request);
        Assertions.assertEquals(certificate.getCertificateName(), certificates.get(0).getCertificateName());
    }

    @Test
    public void testDelete() throws LogicException {
        Certificate certificate = getCertificate();
        certificateLogic.addCertificate(certificate);
        SearchCertificateRequest request = getRequest();
        List<Certificate> certificates = certificateLogic.findCertificates(request);
        Assertions.assertEquals("TEST", certificates.get(0).getCertificateName());
        int id = certificates.get(0).getGiftCertificateId();
        DeleteByIdRequest request1 = new DeleteByIdRequest();
        request1.setId(id);
        certificateLogic.deleteCertificate(request1);
        LogicException thrown = Assertions.assertThrows(LogicException.class,
                () -> certificateLogic.findCertificates(new SearchCertificateRequest()));
        Assertions.assertEquals("No certificates found", thrown.getMessage());
    }

    @Test
    public void testUpdate() throws LogicException {
        Certificate certificate = getCertificate();
        certificateLogic.addCertificate(certificate);
        SearchCertificateRequest request = getRequest();
        List<Certificate> certificates = certificateLogic.findCertificates(request);
        Assertions.assertEquals("TEST", certificates.get(0).getCertificateName());
        int id = certificates.get(0).getGiftCertificateId();
        UpdateCertificateRequest request1 = new UpdateCertificateRequest();
        request1.setGiftCertificateId(id);
        request1.setCertificateName("NEW_TEST");
        certificateLogic.updateCertificate(request1);
        Certificate newCertificate = certificateLogic.findCertificates(new SearchCertificateRequest()).get(0);
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

    public static SearchCertificateRequest getRequest() {
        SearchCertificateRequest request = new SearchCertificateRequest();
        request.setCertificateName("TEST");
        return request;
    }
}