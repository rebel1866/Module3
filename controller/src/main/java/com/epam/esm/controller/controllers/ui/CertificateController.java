package com.epam.esm.controller.controllers.ui;

import com.epam.esm.logic.dto.DeleteByIdRequest;
import com.epam.esm.logic.dto.SearchCertificateRequest;
import com.epam.esm.logic.dto.SearchTagRequest;
import com.epam.esm.controller.exceptions.UiControllerException;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.logic.exceptions.LogicException;
import com.epam.esm.logic.interfaces.CertificateLogic;
import com.epam.esm.logic.interfaces.TagLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CertificateController {

    private CertificateLogic certificateLogic;
    private TagLogic tagLogic;

    @Autowired
    public void setCertificateLogic(CertificateLogic certificateLogic) {
        this.certificateLogic = certificateLogic;
    }

    @Autowired
    public void setTagLogic(TagLogic tagLogic) {
        this.tagLogic = tagLogic;
    }

    @RequestMapping(value = "/certificates", method = {RequestMethod.POST})
    public String showCertificates(@ModelAttribute SearchCertificateRequest searchRequest, Model model) throws UiControllerException {
        List<Certificate> certificates;
        try {
            certificates = certificateLogic.findCertificates(searchRequest);
        } catch (LogicException e) {
            if (e.getErrorCode().equals("errorCode=1")) {
                model.addAttribute("message", "Nothing found");
                return "certificates";
            } else {
                throw new UiControllerException(e.getMessage(), e);
            }
        }
        model.addAttribute("params", searchRequest);
        model.addAttribute("certificates", certificates);
        return "certificates";
    }

    @RequestMapping(value = "/certificates")
    public String showCertificatesGet(Model model) throws UiControllerException {
        List<Certificate> certificates;
        try {
            certificates = certificateLogic.findCertificates(new SearchCertificateRequest());
        } catch (LogicException e) {
            throw new UiControllerException(e.getMessage(), e);
        }
        model.addAttribute("certificates", certificates);
        return "certificates";
    }

    @RequestMapping(value = "/add-certificate-form")
    public String showAddCertificateForm(Model model) throws UiControllerException {
        List<Tag> tags;
        try {
            tags = tagLogic.findTags(new SearchTagRequest());
        } catch (LogicException e) {
            throw new UiControllerException(e.getMessage(), e);
        }
        model.addAttribute("certificate", new Certificate());
        model.addAttribute("tags", tags);
        return "add-certificate";
    }

    @RequestMapping(value = "/addCertificate", method = RequestMethod.POST)
    public String addCertificate(@ModelAttribute @Valid Certificate certificate, BindingResult bindingResult, Model model)
            throws UiControllerException {
        if (bindingResult.hasErrors()) {
            List<Tag> tags;
            try {
                tags = tagLogic.findTags(new SearchTagRequest());
            } catch (LogicException e) {
                throw new UiControllerException(e.getMessage(), e);
            }
            model.addAttribute("tags", tags);
            return "add-certificate";
        }
        try {
            certificateLogic.addCertificate(certificate);
        } catch (LogicException e) {
            throw new UiControllerException(e.getMessage(), e);
        }
        return "redirect:/certificates";
    }


    @RequestMapping(value = "/delete-certificate", method = RequestMethod.POST)
    public String deleteCertificate(@ModelAttribute DeleteByIdRequest request) throws UiControllerException {
        try {
            certificateLogic.deleteCertificate(request);
        } catch (LogicException e) {
            throw new UiControllerException(e.getMessage(), e);
        }
        return "redirect:/certificates";
    }
}
