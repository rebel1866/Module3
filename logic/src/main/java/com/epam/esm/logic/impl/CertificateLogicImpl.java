package com.epam.esm.logic.impl;

import com.epam.esm.converter.CertificateDtoToEntityConverter;
import com.epam.esm.converter.CertificateEntityToDtoConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.SearchCertificateRequest;
import com.epam.esm.dto.UpdateCertificateRequest;
import com.epam.esm.exception.DaoException;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.LogicException;
import com.epam.esm.logic.CertificateLogic;
import com.epam.esm.converter.ObjectToMapConverter;
import com.google.common.base.CaseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CertificateLogicImpl implements CertificateLogic {

    private CertificateDao certificateDao;
    private static final String dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @Autowired
    public void setCertificateDao(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    @Transactional
    public List<CertificateDto> findCertificates(SearchCertificateRequest request) {
        Map<String, String> params = ObjectToMapConverter.convertToMap(request);
        var iterator = params.entrySet().iterator();
        Map<String, String> newParams = new HashMap<>();
        while (iterator.hasNext()) {
            var el = iterator.next();
            String key = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, el.getKey());
            String value = String.valueOf(el.getValue());
            newParams.put(key, value);
        }
        List<Certificate> certificates;
        certificates = certificateDao.findCertificates(newParams);
        return CertificateEntityToDtoConverter.convertList(certificates);
    }

    @Override
    @Transactional
    public CertificateDto findCertificateById(int id) {
        validateId(id);
        Certificate certificate = certificateDao.findCertificateById(id);
        return CertificateEntityToDtoConverter.convert(certificate);
    }

    @Override
    @Transactional
    public CertificateDto addCertificate(CertificateDto request) {
        Certificate certificate = CertificateDtoToEntityConverter.convert(request);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        LocalDateTime now = LocalDateTime.parse(formatter.format(LocalDateTime.now()));
        certificate.setCreationDate(now);
        certificate.setLastUpdateTime(now);
        Certificate addedCertificate;
        addedCertificate = certificateDao.addCertificate(certificate);
        return CertificateEntityToDtoConverter.convert(addedCertificate);
    }

    @Override
    public void deleteCertificate(int id) {
        validateId(id);
        certificateDao.deleteCertificate(id);
    }

    @Override
    @Transactional
    public CertificateDto updateCertificate(UpdateCertificateRequest request, int id) {
        validateId(id);
        Map<String, String> params = ObjectToMapConverter.convertToMap(request);
        Certificate certificate;
        certificate = certificateDao.updateCertificate(params, id);
        return CertificateEntityToDtoConverter.convert(certificate);
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new LogicException("messageCode10", "errorCode=3");
        }
    }
}
