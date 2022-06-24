package com.epam.esm.logic;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.SearchCertificateRequest;
import com.epam.esm.dto.UpdateCertificateRequest;
import com.epam.esm.exception.LogicException;

import java.util.List;

public interface CertificateLogic {
    List<CertificateDto> findCertificates(SearchCertificateRequest request) throws LogicException;

    CertificateDto addCertificate(CertificateDto request) throws LogicException;

    void deleteCertificate(int id) throws LogicException;

    CertificateDto updateCertificate(UpdateCertificateRequest request, int id) throws LogicException;

    CertificateDto findCertificateById(int id) throws LogicException;
}
