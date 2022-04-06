package com.epam.esm.converter;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;

public class CertificateDtoToEntityConverter {
    public static Certificate convert(CertificateDto request){
        Certificate certificate = new Certificate();
        certificate.setCertificateName(request.getCertificateName());
        certificate.setPrice(request.getPrice());
        certificate.setDuration(request.getDuration());
        certificate.setTags(request.getTags());
        certificate.setDescription(request.getDescription());
        return certificate;
    }
}
