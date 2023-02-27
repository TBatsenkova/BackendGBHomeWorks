package lesson4.dto;

import io.restassured.response.Response;
import lesson4.dto.api.*;
import lesson4.dto.response.AddToMealResponse;
import lesson4.dto.response.ConnectUserResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostTest extends AbstractTest {

    @BeforeEach
    public void configureSpecifications() {
        Specifications.installSpecifications(Specifications.requestSpecification(), Specifications.responseSpecUnique(200));
    }

    @Test
    void connectUserTest() {
        ConnectUser user = new ConnectUser("batsenkova", "tatiana", "batsenkova", "newbox@bk.ru");
        ConnectUserResponse connectUser =
                given()
                .body(user)
                .expect()
                .body("status", equalTo("success"))
                .when()
                .post(getBaseUrl() + "users/connect")
                .then()
                .extract()
                .body().as(ConnectUserResponse.class);
        assertThat(connectUser.getStatus(), equalTo("success"));
        System.out.println(connectUser.getHash());
    }

}
