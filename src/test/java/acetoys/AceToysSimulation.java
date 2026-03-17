package acetoys;

import acetoys.pageobjects.*;
import acetoys.simulation.TestPopulation;
import acetoys.simulation.TestScenario;
import acetoys.simulation.UserJourney;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AceToysSimulation extends Simulation {

    private static final String TEST_TYPE = System.getenv("TEST_TYPE");
    private static final String DOMAIN = "acetoys.uk";

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://" + DOMAIN)
            .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("en-GB,en;q=0.9");

//    NOT NEEDED ANYMORE AFTER TestScenario
//    private final ScenarioBuilder scn = scenario("AceToysSimulation")
//            .exec(UserJourney.completePurchase);

//            DEFAULT USER JOURNEY IMPLEMENTED BEFORE UserSession existed
//            .exec(UserSession.initSession)
//            .exec(StaticPages.homepage)
//            .pause(2)
//            .exec(StaticPages.ourStory)
//            .pause(2)
//            .exec(StaticPages.getInTouch)
//            .pause(2)
//            .exec(Category.productsListByCategory)
//            .pause(2)
//            .exec(Category.cyclePagesOfProducts)
//            .pause(2)
//            .exec(Category.cyclePagesOfProducts)
//            .pause(2)
//            .exec(Product.loadProductDetailsPage)
//            .pause(2)
//            .exec(Product.addProductToCart)
//            .pause(2)
//            .exec(Category.productsListByCategory)
//            .pause(2)
//            .exec(Product.addProductToCart)
//            .pause(2)
//            .exec(Cart.viewCart)
//            .pause(2)
//            // .exec(Customer.login) -> Included in viewCart method
//            .exec( Customer.printToken) // used in debugging. You can print the token if you want.
//            .pause(2)
//            .exec(Cart.increaseQuantityInCart)
//            .pause(2)
//            .exec(Cart.increaseQuantityInCart)
//            .pause(2)
//            .exec(Cart.decreaseQuantityInCart)
//            .pause(2)
//            .exec(Cart.viewCart)
//            .pause(2)
//            .exec(Cart.checkout)
//            .pause(2)
//            .exec(Customer.logout);
    {
        if (TEST_TYPE.equals("INSTANT_USERS")) {
            setUp(TestPopulation.instantUsers).protocols(httpProtocol);
        } else if (TEST_TYPE.equals("RAMP_USERS")) {
            setUp(TestPopulation.rampUsers).protocols(httpProtocol);
        } else if (TEST_TYPE.equals("COMPLEX_INJECTION")) {
            setUp(TestPopulation.rampUsers).protocols(httpProtocol);
        } else if (TEST_TYPE.equals("CLOSE_MODE")) {
            setUp(TestPopulation.closedMode).protocols(httpProtocol);
        } else {
            setUp(TestPopulation.instantUsers).protocols(httpProtocol);
        }
    }
}
