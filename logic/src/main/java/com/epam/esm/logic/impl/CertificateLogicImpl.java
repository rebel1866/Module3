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
    public List<CertificateDto> findCertificates(SearchCertificateRequest request) throws LogicException {
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
        try {
            certificates = certificateDao.findCertificates(newParams);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e.getErrorCode(), e);
        }
        return CertificateEntityToDtoConverter.convertList(certificates);
    }

    // validation id в отдельный класс?
    @Override
    public CertificateDto findCertificateById(int id) throws LogicException {
        if (id <= 0) {
            throw new LogicException("Id must be positive integer number", "errorCode=3");
        }
        try {
            Certificate certificate = certificateDao.findCertificateById(id);
            return CertificateEntityToDtoConverter.convert(certificate);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e.getErrorCode(), e);
        }
    }

    @Override
    public CertificateDto addCertificate(CertificateDto request) throws LogicException {
        Certificate certificate = CertificateDtoToEntityConverter.convert(request);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        LocalDateTime now = LocalDateTime.parse(formatter.format(LocalDateTime.now()));
        certificate.setCreationDate(now);
        certificate.setLastUpdateTime(now);
        Certificate addedCertificate;
        try {
            addedCertificate = certificateDao.addCertificate(certificate);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e.getErrorCode(), e);
        }
        return CertificateEntityToDtoConverter.convert(addedCertificate);
    }

    @Override
    public void deleteCertificate(int id) throws LogicException {
        if (id <= 0) {
            throw new LogicException("Id must be positive integer number", "errorCode=3");
        }
        try {
            certificateDao.deleteCertificate(id);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e.getErrorCode(), e);
        }
    }

    @Override
    public CertificateDto updateCertificate(UpdateCertificateRequest request, int id) throws LogicException {
        if (id <= 0) {
            throw new LogicException("Id must be positive integer number", "errorCode=3");
        }
        Map<String, String> params = ObjectToMapConverter.convertToMap(request);
        Certificate certificate;
        try {
            certificate = certificateDao.updateCertificate(params, id);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e.getErrorCode(), e);
        }
        return CertificateEntityToDtoConverter.convert(certificate);
    }
}
