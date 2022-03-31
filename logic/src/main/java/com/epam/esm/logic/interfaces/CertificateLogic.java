package com.epam.esm.logic.interfaces;

import com.epam.esm.logic.dto.DeleteByIdRequest;
import com.epam.esm.logic.dto.SearchCertificateRequest;
import com.epam.esm.logic.dto.UpdateCertificateRequest;
import com.epam.esm.entity.Certificate;
import com.epam.esm.logic.exceptions.LogicException;

import java.util.List;

public interface CertificateLogic {
    List<Certificate> findCertificates(SearchCertificateRequest request) throws LogicException;

    void addCertificate(Certificate certificate) throws LogicException;

    void deleteCertificate(DeleteByIdRequest request) throws LogicException;

    void updateCertificate(UpdateCertificateRequest request) throws LogicException;
}
