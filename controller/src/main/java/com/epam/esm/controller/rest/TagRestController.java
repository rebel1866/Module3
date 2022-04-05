package com.epam.esm.controller.rest;

import com.epam.esm.dto.AddTagRequest;
import com.epam.esm.dto.SearchTagRequest;
import com.epam.esm.exception.RestControllerException;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.LogicException;
import com.epam.esm.logic.TagLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TagRestController {
    private TagLogic tagLogic;

    @Autowired
    public void setTagLogic(TagLogic tagLogic) {
        this.tagLogic = tagLogic;
    }

    @GetMapping(value = "/tags", consumes = {"application/json"}, produces = {"application/json"})
    public List<Tag> getCertificates(@ModelAttribute @Valid SearchTagRequest request, BindingResult bindingResult)
            throws RestControllerException {
        if (bindingResult.hasErrors())
            throw new RestControllerException("Wrong input data", "errorCode=3", bindingResult);
        try {
            return tagLogic.findTags(request);
        } catch (LogicException e) {
            throw new RestControllerException(e.getMessage(), e.getErrorCode(), e);
        }
    }

    @GetMapping(value = "/tags/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public Tag getCertificateById(@PathVariable("id") int id) throws RestControllerException {
        try {
            return tagLogic.findTagById(id);
        } catch (LogicException e) {
            throw new RestControllerException(e.getMessage(), e.getErrorCode(), e);
        }
    }

    @PostMapping(value = "/tags", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public void addCertificate(@RequestBody @Valid AddTagRequest request, BindingResult bindingResult)
            throws RestControllerException {
        if (bindingResult.hasErrors())
            throw new RestControllerException("Wrong input data", "errorCode=3", bindingResult);
        try {
            tagLogic.addTag(request);
        } catch (LogicException e) {
            throw new RestControllerException(e.getMessage(), e.getErrorCode(), e);
        }
    }

    @DeleteMapping(value = "/tags/{id}", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable("id") int id) throws RestControllerException {
        try {
            tagLogic.deleteTag(id);
        } catch (LogicException e) {
            throw new RestControllerException(e.getMessage(), e.getErrorCode(), e);
        }
    }
}
