package tests;

import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.filters;
import static io.restassured.RestAssured.baseURI;
import static listeners.CustomAllureListener.customAllureTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.LiisTestsComponentsOld.*;

@DisplayName("LIIS API-autotests v.1")
public class LiisTestsOld {
    private static final String
            USERNAME = "admin",
            PASSWORD = "123";

    private static final Integer
            VERSION = 1;

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

        Integer postID = postPost(USERNAME, PASSWORD, "Title", "Content").path("id");
        String postTitle = getPost(postID).path("title");
        String postContent = getPost(postID).path("content");
        assertThat(postTitle).isEqualTo("Title");
        assertThat(postContent).isEqualTo("Content");

        putPost(USERNAME, PASSWORD, "NewTitle", "NewContent", postID);
        postTitle = getPost(postID).path("title");
        postContent = getPost(postID).path("content");
        assertThat(postTitle).isEqualTo("NewTitle");
        assertThat(postContent).isEqualTo("NewContent");

        deletePost(USERNAME, PASSWORD, postID);

        String postsAfter = getPosts().asString();

        assertThat(postsBefore).isEqualTo(postsAfter);
    }

    @Test
    @Owner("KELONMYOSA")
    @DisplayName("Testing requests for comments")
    void commentsTest() {
        String commentsBefore = getComments().asString();

        Integer postID = postPost(USERNAME, PASSWORD, "Title", "Content").path("id");

        Integer commentID = postComment(USERNAME, PASSWORD, "TitleCom", "ContentCom", postID).path("id");
        String commentTitle = getComment(commentID).path("title");
        String commentContent = getComment(commentID).path("content");
        assertThat(commentTitle).isEqualTo("TitleCom");
        assertThat(commentContent).isEqualTo("ContentCom");

        putComment(USERNAME, PASSWORD, "NewTitleCom", "NewContentCom", commentID);
        commentTitle = getComment(commentID).path("title");
        commentContent = getComment(commentID).path("content");
        assertThat(commentTitle).isEqualTo("NewTitleCom");
        assertThat(commentContent).isEqualTo("NewContentCom");

        deleteComment(USERNAME, PASSWORD, commentID);
        deletePost(USERNAME, PASSWORD, postID);

        String commentsAfter = getComments().asString();

        assertThat(commentsBefore).isEqualTo(commentsAfter);
    }

    @Test
    @Owner("KELONMYOSA")
    @DisplayName("Sign-in test")
    void signInTest() {
        Response signInResponse = signIn("ruby", "youremadsil", "123");
        String response = String.valueOf(signInResponse.statusCode());
        if (!response.equals("200")) {
            response = signInResponse.path("message");
        }
        assertThat(response).isIn("User with this username or email already exists", "200");
    }
}
