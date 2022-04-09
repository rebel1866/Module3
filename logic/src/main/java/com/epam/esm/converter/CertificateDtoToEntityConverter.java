package com.epam.esm.converter;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CertificateDtoToEntityConverter {
    public static Certificate convert(CertificateDto request) {
        Certificate certificate = new Certificate();
        certificate.setCertificateName(request.getCertificateName());
        certificate.setPrice(request.getPrice());
        certificate.setDuration(request.getDuration());
        certificate.setTags(convertTags(request.getTags()));
        certificate.setDescription(request.getDescription());
        return certificate;
    }

    private static List<Tag> convertTags(List<TagDto> sourceTags) {
        return sourceTags.stream().map(l -> {
            Tag tag = new Tag();
            tag.setTagName(l.getTagName());
            tag.setTagId(l.getTagId());
            return tag;
        }).collect(Collectors.toList());
    }
}
