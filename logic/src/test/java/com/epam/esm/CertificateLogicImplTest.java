package com.epam.esm;


import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.dto.SearchCertificateRequest;
import com.epam.esm.dto.UpdateCertificateRequest;
import com.epam.esm.exception.LogicException;
import com.epam.esm.logic.CertificateLogic;
import com.epam.esm.testconfig.LogicTestConfig;
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
import java.util.HashMap;
import java.util.Map;


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
    @Captor
    private ArgumentCaptor<Map<String, String>> captorMap;

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
    public void addCertificateTest() {
        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setTags(new ArrayList<>());
        Certificate c = new Certificate();
        c.setCertificateName("test");
        c.setTags(new ArrayList<>());
        Mockito.when(certificateDao.addCertificate(Mockito.any(Certificate.class))).thenReturn(c);
        CertificateDto newDto = certificateLogic.addCertificate(certificateDto);
        Assertions.assertEquals("test", newDto.getCertificateName());
        Mockito.verify(certificateDao).addCertificate(captor.capture());
        Assertions.assertNotNull(captor.getValue().getCreationDate());
        Assertions.assertNotNull(captor.getValue().getLastUpdateTime());
    }

    @Test
    public void getCertificatesTest() {
        SearchCertificateRequest request = new SearchCertificateRequest();
        request.setCertificateName("test");
        certificateLogic.findCertificates(request);
        Mockito.verify(certificateDao).findCertificates(captorMap.capture());
        Assertions.assertNotNull(captorMap.getValue().get("certificate_name"));
    }

    @Test
    public void getCertificatesByIdTest() {
        Certificate certificate = new Certificate();
        certificate.setCertificateName("test");
        certificate.setTags(new ArrayList<>());
        Mockito.when(certificateDao.findCertificateById(5)).thenReturn(certificate);
        Assertions.assertEquals("test", certificateLogic.findCertificateById(5).getCertificateName());
        LogicException logicException = Assertions.assertThrows(LogicException.class, () -> certificateLogic.findCertificateById(-5));
        Assertions.assertEquals("messageCode10", logicException.getMessage());
    }

    @Test
    public void deleteCertificateTest() {
        LogicException logicException = Assertions.assertThrows(LogicException.class, () -> certificateLogic.deleteCertificate(-5));
        Assertions.assertEquals("messageCode10", logicException.getMessage());
    }

    @Test
    public void testUpdate() {
        UpdateCertificateRequest request = new UpdateCertificateRequest();
        request.setCertificateName("test");
        Certificate certificate = new Certificate();
        certificate.setCertificateName("test");
        certificate.setTags(new ArrayList<>());
        Map<String, String> map = new HashMap<>();
        map.put("certificateName", "test");
        Mockito.when(certificateDao.updateCertificate(map, 2)).thenReturn(certificate);
        CertificateDto dto = certificateLogic.updateCertificate(request, 2);
        ArgumentCaptor<Integer> captorInt = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(certificateDao).updateCertificate(captorMap.capture(), captorInt.capture());
        Assertions.assertEquals("test", captorMap.getValue().get("certificateName"));
        LogicException logicException = Assertions.assertThrows(LogicException.class, () -> certificateLogic.updateCertificate(request, -5));
        Assertions.assertEquals("messageCode10", logicException.getMessage());
        Assertions.assertEquals(certificate.getCertificateName(), dto.getCertificateName());
    }
}