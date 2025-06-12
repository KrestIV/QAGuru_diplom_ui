package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class ProfileSpec {
    public static RequestSpecification profileSpecRequest = with()
            .filter(withCustomTemplates())
            .log().all()
            .contentType(JSON);
    public static ResponseSpecification profileSpecResponse = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();
}
