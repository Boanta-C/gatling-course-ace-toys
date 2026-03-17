package acetoys.pageobjects;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.http.HttpDsl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Customer {

    private static final Iterator<Map<String, Object>> loginFeeder =
            Stream.generate((Supplier<Map<String, Object>>) () -> {
                Random rand = new Random();
                int userId = rand.nextInt(3 - 1 + 1) + 1;

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("userId", "user" + userId);
                hashMap.put("password", "pass");
                return hashMap;
            }).iterator();

    public static ChainBuilder login =
            feed(loginFeeder).exec(
                    http("Login User")
                            .post("/login")
                            .formParam("_csrf", "#{csrfToken}")
                            .formParam("username", "#{userId}")
                            .formParam("password", "#{password}")
                            .check(css("#_csrf", "content").saveAs("csrfToken"))) //or I can save it as another token if I want
                    .exec(session -> session.set("customerLoggedIn", true));

    public static ChainBuilder logout =
            randomSwitch().on(
                    percent(10)
                            .then(exec (http("Logout")
                                    .post("/logout")
                                    .formParam("_csrf", "#{csrfToken}")
                                    .check(css("nav ul:nth-of-type(2) li a").is("Login"))
                    )));

    public static ChainBuilder printToken =
            exec(
                    session -> {
                        System.out.println(session);
                        System.out.println("csrfToken value is: " + session.getString("csrfToken"));
                        return session;
                    }
            );

}
