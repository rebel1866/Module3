package com.epam.esm;


import com.epam.esm.dao.TagDao;


import com.epam.esm.dto.SearchTagRequest;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.LogicException;
import com.epam.esm.logic.TagLogic;
import com.epam.esm.testconfig.LogicTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LogicTestConfig.class)
@WebAppConfiguration
class TagLogicImplTest {
    @InjectMocks
    private TagLogic tagLogic;

    @Mock
    private TagDao tagDao;
    @Captor
    private ArgumentCaptor<Tag> captor;
    @Captor
    private ArgumentCaptor<Map<String, String>> captorMap;

    @Autowired
    public void setTagLogic(TagLogic tagLogic) {
        this.tagLogic = tagLogic;
    }

    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }


    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getTagsByParamsTest() {
        SearchTagRequest request = new SearchTagRequest();
        request.setTagName("test");
        tagLogic.findTags(request);
        Mockito.verify(tagDao).findTags(captorMap.capture());
        Assertions.assertNotNull(captorMap.getValue().get("tag_name"));
    }

    @Test
    void getTagByIdTest() {
        Tag tag = new Tag();
        tag.setTagName("new");
        Mockito.when(tagDao.findTagById(5)).thenReturn(tag);
        Assertions.assertEquals("new", tagLogic.findTagById(5).getTagName());
        LogicException logicException = Assertions.assertThrows(LogicException.class, () -> tagLogic.findTagById(-5));
        Assertions.assertEquals("messageCode10", logicException.getMessage());
    }

    @Test
    void addTagTest(){
        Tag tag = new Tag();
        tag.setTagName("new");
        Mockito.when(tagDao.addTag(tag)).thenReturn(tag);
        TagDto dto = tagLogic.addTag("new");
        Mockito.verify(tagDao).addTag(captor.capture());
        assertEquals("new", captor.getValue().getTagName());
        assertEquals("new", dto.getTagName());
    }

    @Test
    void deleteTagTest() {
        LogicException logicException = Assertions.assertThrows(LogicException.class, () -> tagLogic.deleteTag(-5));
        Assertions.assertEquals("messageCode10", logicException.getMessage());
    }
}