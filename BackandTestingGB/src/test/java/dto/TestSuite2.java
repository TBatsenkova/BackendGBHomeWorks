package lesson4.dto;

import io.restassured.response.Response;
import lesson4.dto.api.Item;
import lesson4.dto.request.AddToShoppingListRequest;
import lesson4.dto.response.AddToShoppingListResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public class TestSuite2 extends AbstractTest {

    @BeforeEach
    public void configureSpecifications() {
        Specifications.installSpecifications(Specifications.requestSpecWithHash(), Specifications.responseSpecUnique(200));
    }

    @Test
    void shoppingListTest() {
        AddToShoppingListRequest vanillaCream = new AddToShoppingListRequest("1 package coconut powder", "Baking", true);
        AddToShoppingListResponse shoppingList =
                given()
                        .body(vanillaCream)
                        .pathParam("username", getUsername())
                        .when()
                        .post("mealplanner/{username}/shopping-list/items/")
                        .then()
                        .extract()
                        .body().as(AddToShoppingListResponse.class);
        assertThat(shoppingList.getAisle(), equalTo("Baking"));
        assertThat(shoppingList.getName(), equalTo("coconut powder"));

        /**без POJO класса, тк проверяем только статус
         * */
        Response deleteItemFromSoppingList =
                given()
                        .pathParam("username", getUsername())
                        .delete("mealplanner/{username}/shopping-list/items/" + shoppingList.getId())
                        .then()
                        .body("status", equalTo("success"))
                        .extract()
                        .response();

        /**проверяем, что Item действительно удален
        * */
        List<Object> names =
                given()
                        .pathParam("username", getUsername())
                        .when()
                        .get("mealplanner/{username}/shopping-list")
                        .then()
                        .extract().jsonPath().getList("aisles.items.name");

        assert !names.toString().contains("coconut powder");


    }
}
