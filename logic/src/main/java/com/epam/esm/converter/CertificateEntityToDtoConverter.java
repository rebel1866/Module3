package com.epam.esm.converter;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.stream.Collectors;

public class CertificateEntityToDtoConverter {
    public static CertificateDto convert(Certificate certificate){
        CertificateDto dto = new CertificateDto();
        dto.setCertificateName(certificate.getCertificateName());
        dto.setPrice(certificate.getPrice());
        dto.setDescription(certificate.getDescription());
        dto.setDuration(certificate.getDuration());
        dto.setGiftCertificateId(certificate.getGiftCertificateId());
        dto.setTags(certificate.getTags());
        dto.setCreationDate(certificate.getCreationDate());
        dto.setLastUpdateTime(certificate.getLastUpdateTime());
        return dto;
    }
    public static List<CertificateDto> convertList(List<Certificate> certificates){
        return certificates.stream().map(CertificateEntityToDtoConverter::convert).collect(Collectors.toList());
    }
}
