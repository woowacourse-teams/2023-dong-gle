package org.donggle.backend.ui;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.donggle.backend.application.repository.TokenRepository;
import org.donggle.backend.application.service.auth.AuthFacadeService;
import org.donggle.backend.application.service.category.CategoryService;
import org.donggle.backend.application.service.member.MemberService;
import org.donggle.backend.application.service.publish.PublishFacadeService;
import org.donggle.backend.application.service.trash.TrashService;
import org.donggle.backend.application.service.writing.WritingFacadeService;
import org.donggle.backend.domain.auth.JwtTokenProvider;
import org.donggle.backend.infrastructure.client.medium.MediumConnectionClient;
import org.donggle.backend.infrastructure.client.notion.NotionConnectionClient;
//import org.donggle.backend.infrastructure.client.tistory.TistoryApiClient;
//import org.donggle.backend.infrastructure.client.tistory.TistoryConnectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({
        AuthController.class,
//        BlogController.class,
        CategoryController.class,
        ConnectionController.class,
        MemberController.class,
        PublishController.class,
        TrashController.class,
        WritingController.class
})
public abstract class ControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected MemberService memberService;
    @MockBean
    protected AuthFacadeService authFacadeService;
    @MockBean
    protected JwtTokenProvider jwtTokenProvider;
    @MockBean
    protected TokenRepository tokenRepository;
//    @MockBean
//    protected TistoryApiClient tistoryApiService;
    @MockBean
    protected CategoryService categoryService;
//    @MockBean
//    protected TistoryConnectionClient tistoryConnectService;
    @MockBean
    protected NotionConnectionClient notionConnectionService;
    @MockBean
    protected MediumConnectionClient mediumConnectionClient;
    @MockBean
    protected PublishFacadeService publishFacadeService;
    @MockBean
    protected TrashService trashService;
    @MockBean
    protected WritingFacadeService writingFacadeService;
}
