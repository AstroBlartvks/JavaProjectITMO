package AstroLabServer.auth;

public class DataConfig {
    public static final String PEPPER = System.getenv("PEPPER") == null
                                        ? "TheDailyBibleReadingPlanIsAnEasyWayToReadTheBibleInOneYear"
                                        : System.getenv("PEPPER");
}
