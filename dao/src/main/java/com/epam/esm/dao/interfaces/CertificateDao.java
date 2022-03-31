package com.epam.esm.dao.interfaces;

import com.epam.esm.dao.exceptions.DaoException;
import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Map;

public interface CertificateDao {
    List<Certificate> findCertificates(Map<String,String> params) throws DaoException;
    void addCertificate(Certificate certificate) throws DaoException;

    void deleteCertificate(int id) throws DaoException;

    void updateCertificate(Map<String, String> params) throws DaoException;
}
