package com.epam.esm.controller.ui;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.UiControllerException;
import com.epam.esm.dto.SearchCertificateRequest;
import com.epam.esm.dto.SearchTagRequest;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.LogicException;
import com.epam.esm.logic.CertificateLogic;
import com.epam.esm.logic.TagLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        List<CertificateDto> certificates;
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
        List<CertificateDto> certificates;
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
    public String addCertificate(@ModelAttribute @Valid CertificateDto request, BindingResult bindingResult, Model model)
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
            certificateLogic.addCertificate(request);
        } catch (LogicException e) {
            throw new UiControllerException(e.getMessage(), e);
        }
        return "redirect:/certificates";
    }


    @RequestMapping(value = "/delete-certificate/{id}", method = RequestMethod.POST)
    public String deleteCertificate(@PathVariable("id") int id) throws UiControllerException {
        try {
            certificateLogic.deleteCertificate(id);
        } catch (LogicException e) {
            throw new UiControllerException(e.getMessage(), e);
        }
        return "redirect:/certificates";
    }
}
