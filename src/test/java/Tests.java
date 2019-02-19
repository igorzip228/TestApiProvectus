import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import io.restassured.RestAssured;
import org.testng.annotations.Test;


public class Tests {
    int userId;

    @Test
    public void getRequest() {
        String response =
                RestAssured.given()
                        .baseUri("https://jsonplaceholder.typicode.com")
                        .basePath("/posts/100")
                        .header("Content-Type", "application/json")
                        .when().get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .response()
                        .prettyPrint();

        ReadContext context = JsonPath.parse(response);

        int userId = context.read("$.userId");
        this.userId = userId;
        int id = context.read("$.id");
        String title = context.read("$.title");
        String body = context.read("$.body");
    }

    @Test
    public void postRequest() {
        String postResponse =
                RestAssured.given()
                        .baseUri("https://jsonplaceholder.typicode.com")
                        .basePath("/posts")
                        .header("Content-Type", "application/json")
                        .body("{\n" +
                                "\"title\": \"foor_your_test_data\",\n" +
                                "\"body\": \"bary_your_test_data\",\n" +
                                "\"userId\": \"" + this.userId + "\"\n" +
                                "}\n")
                        .when().post()
                        .then()
                        .statusCode(201)
                        .extract()
                        .response()
                        .prettyPrint();
        System.err.println(postResponse);
        ReadContext contextPost = JsonPath.parse(postResponse);
        String titlePost = contextPost.read("$.title");
        String bodyPost = contextPost.read("$.body");
        String userIdPost = contextPost.read("$.userId");
        int idPost = contextPost.read("$.id");
    }

}

