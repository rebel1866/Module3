package com.epam.esm.converter;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TagDtoToEntityConverter {
    public static Tag convert(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setTagId(tagDto.getTagId());
        tag.setTagName(tagDto.getTagName());
        return tag;
    }
    public static List<Tag> convertList(List<TagDto> tags){
        if (tags != null) {
            return tags.stream().map(TagDtoToEntityConverter::convert).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
