package com.epam.esm.converter;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class TagEntityToDtoConverter {
    public static TagDto convert(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setTagId(tag.getTagId());
        tagDto.setTagName(tag.getTagName());
        return tagDto;
    }
    public static List<TagDto> convertList(List<Tag> tags){
        return tags.stream().map(TagEntityToDtoConverter::convert).collect(Collectors.toList());
    }
}
