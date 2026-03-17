package acetoys.simulation;

import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;

public class TestScenario {

    private static final Duration TEST_DURATION = Duration.ofSeconds(Integer.
            parseInt(System.getenv("DURATION")));

    public static final ScenarioBuilder defaultLoadTest =
            scenario("Default Load Test")
                    .during(TEST_DURATION)
                    .on(
                            randomSwitch()
                                    .on(
                                            percent(60.0).then(exec(UserJourney.browseStore)),
                                            percent(30.0).then(exec(UserJourney.abandonBasket)),
                                            percent(10.0).then(exec(UserJourney.completePurchase))
                                    )
                    );

    public static final ScenarioBuilder highPurchaseLoadTest =
            scenario("Default Load Test")
                    .during(TEST_DURATION)
                    .on(
                            randomSwitch()
                                    .on(
                                            percent(30.0).then(exec(UserJourney.browseStore)),
                                            percent(30.0).then(exec(UserJourney.abandonBasket)),
                                            percent(40.0).then(exec(UserJourney.completePurchase))
                                    )
                    );

}
