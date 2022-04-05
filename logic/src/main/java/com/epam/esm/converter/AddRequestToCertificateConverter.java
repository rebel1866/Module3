package com.epam.esm.converter;

import com.epam.esm.dto.AddCertificateRequest;
import com.epam.esm.entity.Certificate;

public class AddRequestToCertificateConverter {
    public static Certificate convert(AddCertificateRequest request){
        Certificate certificate = new Certificate();
        certificate.setCertificateName(request.getCertificateName());
        certificate.setPrice(request.getPrice());
        certificate.setDuration(request.getDuration());
        certificate.setTags(request.getTags());
        certificate.setDescription(request.getDescription());
        return certificate;
    }
}
