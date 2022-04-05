package com.epam.esm.controller.rest;

import com.epam.esm.dto.AddCertificateRequest;
import com.epam.esm.exception.RestControllerException;
import com.epam.esm.dto.SearchCertificateRequest;
import com.epam.esm.dto.UpdateCertificateRequest;
import com.epam.esm.entity.Certificate;
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
    public List<Certificate> getCertificates(@ModelAttribute @Valid SearchCertificateRequest searchRequest,
                                             BindingResult bindingResult) throws RestControllerException {
        if (bindingResult.hasErrors())
            throw new RestControllerException("Wrong input data", "errorCode=3", bindingResult);
        try {
            return certificateLogic.findCertificates(searchRequest);
        } catch (LogicException e) {
            throw new RestControllerException(e.getMessage(), e.getErrorCode(), e);
        }
    }
    @GetMapping(value = "/certificates/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public Certificate getCertificateById(@PathVariable("id") int id) throws RestControllerException {
        try {
            return certificateLogic.findCertificateById(id);
        } catch (LogicException e) {
            throw new RestControllerException(e.getMessage(), e.getErrorCode(), e);
        }
    }

    @PostMapping(value = "/certificates", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public void addCertificate(@RequestBody @Valid AddCertificateRequest request,
                               BindingResult bindingResult) throws RestControllerException {
        if (bindingResult.hasErrors())
            throw new RestControllerException("Wrong input data", "errorCode=3", bindingResult);
        try {
            certificateLogic.addCertificate(request);
        } catch (LogicException e) {
            throw new RestControllerException(e.getMessage(), e.getErrorCode(), e);
        }
    }

    @DeleteMapping(value = "/certificates/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<HttpStatus> deleteCertificate(@PathVariable("id") int id) throws RestControllerException {
        try {
            certificateLogic.deleteCertificate(id);
        } catch (LogicException e) {
            throw new RestControllerException(e.getMessage(), e.getErrorCode(), e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //id отдельно. убрать из dto. в path variable
    @PutMapping(value = "/certificates", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<HttpStatus> updateCertificate(@RequestBody @Valid UpdateCertificateRequest request,
                                                        BindingResult result) throws RestControllerException {
        if (result.hasErrors()) {
            throw new RestControllerException("Wrong input data", "errorCode=3", result);
        }
        try {
            certificateLogic.updateCertificate(request);
        } catch (LogicException e) {
            throw new RestControllerException(e.getMessage(), e.getErrorCode(), e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
