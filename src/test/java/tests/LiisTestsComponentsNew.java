package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LiisTestsComponentsNew {
    static Response getPostsPagination(Integer page) {
        return given()
                .contentType(ContentType.JSON)
                .param("page", page)
                .when()
                .get("posts")
                .then()
                .statusCode(200)
                .extract().response();
    }

    static Response postPostNew(String login, String password, String title, String content) {
        String bodyData = "{ \"name\": \"" + title + "\", " +
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

    static Response putPostNew(String login, String password, String title, String content, Integer postID) {
        String bodyData = "{ \"name\": \"" + title + "\", " +
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

    static Response getCommentsPagination(Integer page) {
        return given()
                .contentType(ContentType.JSON)
                .param("page", page)
                .when()
                .get("comments")
                .then()
                .statusCode(200)
                .extract().response();
    }

    static Response postCommentNew(String login, String password, String title, String content, Integer postID) {
        String bodyData = "{ \"title\": \"" + title + "\", " +
                "\"text\": \"" + content + "\", " +
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

    static Response putCommentNew(String login, String password, String title, String content, Integer commentID) {
        String bodyData = "{ \"title\": \"" + title + "\", " +
                "\"text\": \"" + content + "\" }";

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

    static Response signInNew(String username, String email, String password, String firstName, String middleName, String lastName) {
        String bodyData = "{ \"username\": \"" + username + "\", " +
                "\"email\": \"" + email + "\", " +
                "\"password\": \"" + password + "\", " +
                "\"first_name\": \"" + firstName + "\", " +
                "\"middle_name\": \"" + middleName + "\", " +
                "\"last_name\": \"" + lastName + "\" }";

        return given()
                .contentType(ContentType.JSON)
                .body(bodyData)
                .when()
                .post("sign-in")
                .then()
                .extract().response();
    }

    static Response grantAdminRole(String login, String password, Integer userID) {
        String bodyData = "{ \"user_id\": " + userID + " }";

        return given().auth()
                .basic(login, password)
                .contentType(ContentType.JSON)
                .body(bodyData)
                .when()
                .post("make_admin")
                .then()
                .statusCode(200)
                .extract().response();
    }
}
