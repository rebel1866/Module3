package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Map;

public interface CertificateDao {
    List<Certificate> findCertificates(Map<String,String> params) throws DaoException;
    Certificate addCertificate(Certificate certificate) throws DaoException;

    void deleteCertificate(int id) throws DaoException;

    Certificate updateCertificate(Map<String, String> params, int id, List<Tag> tags) throws DaoException;

    Certificate findCertificateById(int id) throws DaoException;
}
