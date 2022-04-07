package com.epam.esm.controller.rest;

import com.epam.esm.converter.SearchTagRequest;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.RestControllerException;
import com.epam.esm.exception.LogicException;
import com.epam.esm.logic.TagLogic;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class TagRestController {
    private TagLogic tagLogic;

    @Autowired
    public void setTagLogic(TagLogic tagLogic) {
        this.tagLogic = tagLogic;
    }

    @GetMapping(value = "/tags", consumes = {"application/json"}, produces = {"application/json"})
    public List<TagDto> getTags(@ModelAttribute @Valid SearchTagRequest request, BindingResult bindingResult)
            throws RestControllerException, LogicException {
        if (bindingResult.hasErrors()) {
            throw new RestControllerException("messageCode11", "errorCode=3", bindingResult);
        }
        return tagLogic.findTags(request);
    }

    @GetMapping(value = "/tags/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public TagDto getTagById(@PathVariable("id") int id) throws LogicException {
        return tagLogic.findTagById(id);
    }

    @PostMapping(value = "/tags", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addCertificate(@RequestBody Map<String,String> param)throws LogicException {
        return tagLogic.addTag(param.get("tagName"));
    }

    @DeleteMapping(value = "/tags/{id}", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable("id") int id) throws LogicException {
        tagLogic.deleteTag(id);
    }
}
