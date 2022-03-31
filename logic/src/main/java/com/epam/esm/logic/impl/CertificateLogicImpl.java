package com.epam.esm.logic.impl;

import com.epam.esm.logic.dto.DeleteByIdRequest;
import com.epam.esm.logic.dto.SearchCertificateRequest;
import com.epam.esm.logic.dto.UpdateCertificateRequest;
import com.epam.esm.dao.exceptions.DaoException;
import com.epam.esm.dao.interfaces.CertificateDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.logic.exceptions.LogicException;
import com.epam.esm.logic.interfaces.CertificateLogic;
import com.epam.esm.logic.logicutils.ObjectToMapConverter;
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

    @Autowired
    public void setCertificateDao( CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    public List<Certificate> findCertificates(SearchCertificateRequest request) throws LogicException {
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
        return certificates;
    }

    @Override
    public void addCertificate(Certificate certificate) throws LogicException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime now = LocalDateTime.parse(formatter.format(LocalDateTime.now()));
        certificate.setCreationDate(now);
        certificate.setLastUpdateTime(now);
        try {
            certificateDao.addCertificate(certificate);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e.getErrorCode(), e);
        }
    }

    @Override
    public void deleteCertificate(DeleteByIdRequest request) throws LogicException {
        int id = request.getId();
        try {
            certificateDao.deleteCertificate(id);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e.getErrorCode(), e);
        }
    }

    @Override
    public void updateCertificate(UpdateCertificateRequest request) throws LogicException {
        Map<String, String> params = ObjectToMapConverter.convertToMap(request);
        try {
            certificateDao.updateCertificate(params);
        } catch (DaoException e) {
            throw new LogicException(e.getMessage(), e.getErrorCode(), e);
        }
    }
}
