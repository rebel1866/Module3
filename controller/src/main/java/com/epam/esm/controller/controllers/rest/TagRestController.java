package com.epam.esm.controller.controllers.rest;

import com.epam.esm.logic.dto.AddTagRequest;
import com.epam.esm.logic.dto.DeleteByIdRequest;
import com.epam.esm.logic.dto.SearchTagRequest;
import com.epam.esm.controller.exceptions.RestControllerException;
import com.epam.esm.entity.Tag;
import com.epam.esm.logic.exceptions.LogicException;
import com.epam.esm.logic.interfaces.TagLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
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
    public List<Tag> showCertificates(@ModelAttribute @Valid SearchTagRequest request, BindingResult bindingResult)
            throws RestControllerException {
        if (bindingResult.hasErrors()) throw new RestControllerException("Wrong input data", "errorCode=3", bindingResult);
        try {
            return tagLogic.findTags(request);
        } catch (LogicException e) {
            throw new RestControllerException(e.getMessage(), e.getErrorCode(), e);
        }
    }

    @PostMapping(value = "/tags", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public void addCertificate(@RequestBody @Valid AddTagRequest request, BindingResult bindingResult)
            throws RestControllerException {
        if (bindingResult.hasErrors()) throw new RestControllerException("Wrong input data", "errorCode=3", bindingResult);
        try {
            tagLogic.addTag(request);
        } catch (LogicException e) {
            throw new RestControllerException(e.getMessage(), e.getErrorCode(), e);
        }
    }

    @DeleteMapping(value = "/tags", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteTag(@ModelAttribute @Valid DeleteByIdRequest request,
                          BindingResult result) throws RestControllerException {
        if (result.hasErrors())
            throw new RestControllerException("Wrong input data - incorrect id", "errorCode=3", result);
        try {
            tagLogic.deleteTag(request);
        } catch (LogicException e) {
            throw new RestControllerException(e.getMessage(), e.getErrorCode(), e);
        }
    }
}
