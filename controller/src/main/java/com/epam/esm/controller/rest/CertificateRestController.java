package com.epam.esm.controller.rest;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.RestControllerException;
import com.epam.esm.dto.SearchCertificateRequest;
import com.epam.esm.dto.UpdateCertificateRequest;
import com.epam.esm.exception.LogicException;
import com.epam.esm.logic.CertificateLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CertificateRestController {
    private CertificateLogic certificateLogic;

    @Autowired
    public void setCertificateLogic(CertificateLogic certificateLogic) {
        this.certificateLogic = certificateLogic;
    }

    @GetMapping(value = "/certificates", consumes = {"application/json"}, produces = {"application/json"})
    public List<CertificateDto> getCertificates(@ModelAttribute @Valid SearchCertificateRequest searchRequest,
                                                BindingResult bindingResult) throws RestControllerException, LogicException {
        if (bindingResult.hasErrors()) {
            throw new RestControllerException("Wrong input data", "errorCode=3", bindingResult);
        }
        return certificateLogic.findCertificates(searchRequest);
    }

    @GetMapping(value = "/certificates/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public CertificateDto getCertificateById(@PathVariable("id") int id) throws LogicException {
        return certificateLogic.findCertificateById(id);
    }

    @PostMapping(value = "/certificates", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDto addCertificate(@RequestBody @Valid CertificateDto request,
                                         BindingResult bindingResult) throws RestControllerException, LogicException {
        if (bindingResult.hasErrors()) {
            throw new RestControllerException("Wrong input data", "errorCode=3", bindingResult);
        }
        return certificateLogic.addCertificate(request);
    }

    @DeleteMapping(value = "/certificates/{id}", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteCertificate(@PathVariable("id") int id) throws LogicException {
        certificateLogic.deleteCertificate(id);
    }

    @PutMapping(value = "/certificates/{id}", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto updateCertificate(@PathVariable("id") int id, @RequestBody @Valid
            UpdateCertificateRequest request, BindingResult result) throws RestControllerException, LogicException {
        if (result.hasErrors()) {
            throw new RestControllerException("Wrong input data", "errorCode=3", result);
        }
        return certificateLogic.updateCertificate(request, id);
    }
}
