package com.epam.esm;


import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.dto.SearchCertificateRequest;
import com.epam.esm.dto.UpdateCertificateRequest;
import com.epam.esm.exception.LogicException;
import com.epam.esm.logic.CertificateLogic;
import com.epam.esm.testconfig.LogicTestConfig;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LogicTestConfig.class)
@WebAppConfiguration
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
        CertificateDto requestAdd = getAddRequest();
        assertDoesNotThrow(() -> certificateLogic.addCertificate(requestAdd));
    }

    @Test
    public void testFind() throws LogicException {
        CertificateDto requestAdd = getAddRequest();
        certificateLogic.addCertificate(requestAdd);
        SearchCertificateRequest request = new SearchCertificateRequest();
        request.setCertificateName("TEST");
        List<CertificateDto> certificates = certificateLogic.findCertificates(request);
        Assertions.assertEquals(requestAdd.getCertificateName(), certificates.get(0).getCertificateName());
    }

    @Test
    public void testDelete() throws LogicException {
        CertificateDto requestAdd = getAddRequest();
        certificateLogic.addCertificate(requestAdd);
        SearchCertificateRequest request = getRequest();
        List<CertificateDto> certificates = certificateLogic.findCertificates(request);
        Assertions.assertEquals("TEST", certificates.get(0).getCertificateName());
        int id = certificates.get(0).getGiftCertificateId();
        certificateLogic.deleteCertificate(id);
        LogicException thrown = Assertions.assertThrows(LogicException.class,
                () -> certificateLogic.findCertificates(new SearchCertificateRequest()));
        Assertions.assertEquals("No certificates found", thrown.getMessage());
    }

    @Test
    public void testUpdate() throws LogicException {
        CertificateDto requestAdd = getAddRequest();
        certificateLogic.addCertificate(requestAdd);
        SearchCertificateRequest request = getRequest();
        List<CertificateDto> certificates = certificateLogic.findCertificates(request);
        Assertions.assertEquals("TEST", certificates.get(0).getCertificateName());
        int id = certificates.get(0).getGiftCertificateId();
        UpdateCertificateRequest request1 = new UpdateCertificateRequest();
        request1.setCertificateName("NEW_TEST");
        certificateLogic.updateCertificate(request1,id);
        CertificateDto newCertificate = certificateLogic.findCertificates(new SearchCertificateRequest()).get(0);
        Assertions.assertEquals("NEW_TEST", newCertificate.getCertificateName());
    }

    @AfterEach
    public void after() {
        JdbcTestUtils.dropTables(jdbcTemplate, "gifts.cert_tags", "gifts.tags", "gifts.gift_certificates");
    }

    public static CertificateDto getAddRequest() {
        CertificateDto request = new CertificateDto();
        request.setCertificateName("TEST");
        Tag tag = new Tag();
        tag.setTagId(1);
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        request.setTags(tags);
        return request;
    }

    public static SearchCertificateRequest getRequest() {
        SearchCertificateRequest request = new SearchCertificateRequest();
        request.setCertificateName("TEST");
        return request;
    }
}