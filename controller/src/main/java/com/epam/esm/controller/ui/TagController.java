package com.epam.esm.controller.ui;

import com.epam.esm.converter.SearchTagRequest;
import com.epam.esm.exception.UiControllerException;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.LogicException;
import com.epam.esm.logic.TagLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class TagController {
    private TagLogic tagLogic;

    @Autowired
    public void setTagLogic(TagLogic tagLogic) {
        this.tagLogic = tagLogic;
    }

    @RequestMapping(value = "/tags", method = {RequestMethod.GET, RequestMethod.POST})
    public String getTags(@ModelAttribute SearchTagRequest request, Model model) throws UiControllerException {
        List<TagDto> tags;
        try {
            tags = tagLogic.findTags(request);
        } catch (LogicException e) {
            if (e.getErrorCode().equals("errorCode=1")) {
                model.addAttribute("message", "Nothing found");
                return "tags";
            } else {
                throw new UiControllerException(e.getMessage(), e);
            }
        }
        model.addAttribute("tags", tags);
        model.addAttribute("params", request);
        return "tags";
    }
}
