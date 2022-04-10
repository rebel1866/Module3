package com.epam.esm.logic.impl;

import com.epam.esm.converter.*;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.SearchCertificateRequest;
import com.epam.esm.dto.UpdateCertificateRequest;
import com.epam.esm.entity.Tag;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.LogicException;
import com.epam.esm.logic.CertificateLogic;
import com.google.common.base.CaseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CertificateLogicImpl implements CertificateLogic {

    private CertificateDao certificateDao;
    private TagDao tagDao;

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @Autowired
    public void setCertificateDao(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public List<CertificateDto> findCertificates(SearchCertificateRequest request) {
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
        certificates = certificateDao.findCertificates(newParams);
        return CertificateEntityToDtoConverter.convertList(certificates);
    }

    @Override
    @Transactional
    public CertificateDto findCertificateById(int id) {
        validateId(id);
        Certificate certificate = certificateDao.findCertificateById(id);
        return CertificateEntityToDtoConverter.convert(certificate);
    }

    @Override
    @Transactional
    public CertificateDto addCertificate(CertificateDto request) {
        Certificate certificate = CertificateDtoToEntityConverter.convert(request);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDateTime now = LocalDateTime.parse(formatter.format(LocalDateTime.now()));
        certificate.setCreationDate(now);
        certificate.setLastUpdateTime(now);
        List<Tag> newTags = getNewTagsToAdd(certificate.getTags());
        Certificate addedCertificate;
        addedCertificate = certificateDao.addCertificate(certificate);
        int certificateId = addedCertificate.getGiftCertificateId();
        List<Tag> tagsThatExist = newTags.stream().map(l -> tagDao.addTag(l)).collect(Collectors.toList());
        for (Tag tag : tagsThatExist) {
            tagDao.addTagToCertificate(tag, certificateId);
        }
        Certificate targetCertificate = certificateDao.findCertificateById(certificateId);
        return CertificateEntityToDtoConverter.convert(targetCertificate);
    }

    @Override
    public void deleteCertificate(int id) {
        validateId(id);
        certificateDao.deleteCertificate(id);
    }

    @Override
    @Transactional
    public CertificateDto updateCertificate(UpdateCertificateRequest request, int id) {
        validateId(id);
        List<Tag> tags = TagDtoToEntityConverter.convertList(request.getTags());
        Map<String, String> params = UpdateDtoToMapConverter.convertToMap(request);
        certificateDao.updateCertificate(params, id);
        if (tags.size() != 0) {
            List<Tag> tagsWithId = tags.stream().map(tag -> tagDao.addTag(tag)).collect(Collectors.toList());
            tagsWithId.forEach(tag -> tagDao.addTagToCertificate(tag, id));
        }
        Certificate certificate = certificateDao.findCertificateById(id);
        return CertificateEntityToDtoConverter.convert(certificate);
    }

    public List<Tag> getNewTagsToAdd(List<Tag> allTags) {
        List<Tag> newTags = new ArrayList<>();
        ListIterator<Tag> iterator = allTags.listIterator();
        while (iterator.hasNext()) {
            Tag tag = iterator.next();
            if (tag.getTagId() != null & tag.getTagName() != null) {
                throw new LogicException("messageCode13", "errorCode=3");
            }
            if (tag.getTagId() == null) {
                newTags.add(tag);
                iterator.remove();
            }
        }
        return newTags;
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new LogicException("messageCode10", "errorCode=3");
        }
    }
}
