package com.epam.esm;


import com.epam.esm.converter.CertificateDtoToEntityConverter;
import com.epam.esm.converter.CertificateEntityToDtoConverter;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.dto.SearchCertificateRequest;
import com.epam.esm.dto.UpdateCertificateRequest;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.LogicException;
import com.epam.esm.logic.CertificateLogic;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LogicTestConfig.class)
@WebAppConfiguration
class CertificateLogicImplTest {

    @InjectMocks
    private CertificateLogic certificateLogic;

    @Mock
    private CertificateDao certificateDao;
    @Captor
    private ArgumentCaptor<Certificate> captor;

    @Autowired
    public void setCertificateLogic(CertificateLogic certificateLogic) {
        this.certificateLogic = certificateLogic;
    }

    public void setCertificateDao(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void addCertificateTest() throws DaoException, LogicException {
        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setTags(new ArrayList<>());
        Certificate c = new Certificate();
        c.setCertificateName("test");
        Mockito.when(certificateDao.addCertificate(Mockito.any(Certificate.class))).thenReturn(c);
        CertificateDto newDto = certificateLogic.addCertificate(certificateDto);
        Assertions.assertEquals("test", newDto.getCertificateName());
        Mockito.verify(certificateDao).addCertificate(captor.capture());
        Assertions.assertNotNull(captor.getValue().getCreationDate());
        Assertions.assertNotNull(captor.getValue().getLastUpdateTime());
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
        certificateLogic.updateCertificate(request1, id);
        CertificateDto newCertificate = certificateLogic.findCertificates(new SearchCertificateRequest()).get(0);
        Assertions.assertEquals("NEW_TEST", newCertificate.getCertificateName());
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