package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LiisTestsComponentsOld {
    static Response getPosts() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("posts")
                .then()
                .statusCode(200)
                .extract().response();
    }

    static Response postPost(String login, String password, String title, String content) {
        String bodyData = "{ \"title\": \"" + title + "\", " +
                "\"content\": \"" + content + "\" }";

        return given().auth()
                .basic(login, password)
                .contentType(ContentType.JSON)
                .body(bodyData)
                .when()
                .post("posts")
                .then()
                .statusCode(201)
                .extract().response();
    }

    static Response getPost(Integer postID) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("post/" + postID)
                .then()
                .statusCode(200)
                .extract().response();
    }

    static Response putPost(String login, String password, String title, String content, Integer postID) {
        String bodyData = "{ \"title\": \"" + title + "\", " +
                "\"content\": \"" + content + "\" }";

        return given().auth()
                .basic(login, password)
                .contentType(ContentType.JSON)
                .body(bodyData)
                .when()
                .put("post/" + postID)
                .then()
                .statusCode(200)
                .extract().response();
    }

    static Response deletePost(String login, String password, Integer postID) {
        return given().auth()
                .basic(login, password)
                .contentType(ContentType.JSON)
                .when()
                .delete("post/" + postID)
                .then()
                .statusCode(204)
                .extract().response();
    }

    static Response getComments() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("comments")
                .then()
                .statusCode(200)
                .extract().response();
    }

    static Response postComment(String login, String password, String title, String content, Integer postID) {
        String bodyData = "{ \"title\": \"" + title + "\", " +
                "\"content\": \"" + content + "\", " +
                "\"post\": " + postID + " }";

        return given().auth()
                .basic(login, password)
                .contentType(ContentType.JSON)
                .body(bodyData)
                .when()
                .post("comments")
                .then()
                .statusCode(201)
                .extract().response();
    }

    static Response getComment(Integer commentID) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("comment/" + commentID)
                .then()
                .statusCode(200)
                .extract().response();
    }

    static Response putComment(String login, String password, String title, String content, Integer commentID) {
        String bodyData = "{ \"title\": \"" + title + "\", " +
                "\"content\": \"" + content + "\" }";

        return given().auth()
                .basic(login, password)
                .contentType(ContentType.JSON)
                .body(bodyData)
                .when()
                .put("comment/" + commentID)
                .then()
                .statusCode(200)
                .extract().response();
    }

    static Response deleteComment(String login, String password, Integer commentID) {
        return given().auth()
                .basic(login, password)
                .contentType(ContentType.JSON)
                .when()
                .delete("comment/" + commentID)
                .then()
                .statusCode(204)
                .extract().response();
    }

    static Response signIn(String username, String email, String password) {
        String bodyData = "{ \"username\": \"" + username + "\", " +
                "\"email\": \"" + email + "\", " +
                "\"password\": \"" + password + "\" }";

        return given()
                .contentType(ContentType.JSON)
                .body(bodyData)
                .when()
                .post("sign-in")
                .then()
                .extract().response();
    }
}
