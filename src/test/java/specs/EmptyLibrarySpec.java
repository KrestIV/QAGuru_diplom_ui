package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;

public class EmptyLibrarySpec {
    public static RequestSpecification emptyLibrarySpecRequest = with()
            .filter(withCustomTemplates())
            .log().all();

    public static ResponseSpecification emptyLibrarySpecResponse = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(ALL)
            .build();
}
