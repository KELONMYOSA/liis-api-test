package tests;

import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.filters;
import static listeners.CustomAllureListener.customAllureTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.LiisTestsComponentsNew.*;
import static tests.LiisTestsComponentsOld.*;

@DisplayName("LIIS API-autotests v.2")
public class LiisTestsNew {
    private static final String
            USERNAME = "admin",
            PASSWORD = "123";

    private static final Integer
            VERSION = 2;

    @BeforeAll
    static void beforeAll() {
        baseURI = "https://hr.recruit.liis.su/qa0/v" + VERSION + "/api/79825223592@yandex.ru/";
        filters(customAllureTemplate());
    }

    @Test
    @Owner("KELONMYOSA")
    @DisplayName("Testing requests for posts")
    void postsTest() {
        String postsBefore = getPosts().asString();

        Integer postID = postPostNew(USERNAME, PASSWORD, "Title", "Content").path("id");
        String postTitle = getPost(postID).path("name");
        String postContent = getPost(postID).path("content");
        assertThat(postTitle).isEqualTo("Title");
        assertThat(postContent).isEqualTo("Content");

        putPostNew(USERNAME, PASSWORD, "NewTitle", "NewContent", postID);
        postTitle = getPost(postID).path("name");
        postContent = getPost(postID).path("content");
        assertThat(postTitle).isEqualTo("NewTitle");
        assertThat(postContent).isEqualTo("NewContent");

        getPostsPagination(1);

        deletePost(USERNAME, PASSWORD, postID);

        String postsAfter = getPosts().asString();

        assertThat(postsBefore).isEqualTo(postsAfter);
    }

    @Test
    @Owner("KELONMYOSA")
    @DisplayName("Testing requests for comments")
    void commentsTest() {
        String commentsBefore = getComments().asString();

        Integer postID = postPostNew(USERNAME, PASSWORD, "Title", "Content").path("id");

        Integer commentID = postCommentNew(USERNAME, PASSWORD, "TitleCom", "ContentCom", postID).path("id");
        String commentTitle = getComment(commentID).path("title");
        String commentContent = getComment(commentID).path("text");
        assertThat(commentTitle).isEqualTo("TitleCom");
        assertThat(commentContent).isEqualTo("ContentCom");

        putCommentNew(USERNAME, PASSWORD, "NewTitleCom", "NewContentCom", commentID);
        commentTitle = getComment(commentID).path("title");
        commentContent = getComment(commentID).path("text");
        assertThat(commentTitle).isEqualTo("NewTitleCom");
        assertThat(commentContent).isEqualTo("NewContentCom");

        getCommentsPagination(1);

        deleteComment(USERNAME, PASSWORD, commentID);
        deletePost(USERNAME, PASSWORD, postID);

        String commentsAfter = getComments().asString();

        assertThat(commentsBefore).isEqualTo(commentsAfter);
    }

    @Test
    @Owner("KELONMYOSA")
    @DisplayName("Sign-in test")
    void signInTest() {
        Response signInResponse = signInNew("ruby", "youremadsil", "123", "First", "Middle", "Last");
        String response = String.valueOf(signInResponse.statusCode());
        if (!response.equals("200")) {
            response = signInResponse.path("message");
        }
        assertThat(response).isIn("User with this username or email already exists", "200");
    }

    @Test
    @Owner("KELONMYOSA")
    @DisplayName("Grant user admin role test")
    void grantAdminRoleTest() {
        grantAdminRole(USERNAME, PASSWORD, 1);
    }
}
