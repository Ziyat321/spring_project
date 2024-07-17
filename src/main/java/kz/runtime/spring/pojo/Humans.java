package kz.runtime.spring.pojo;

public class Humans {
    private static final Human[] humans = {
            new Human("Bill",28),
            new Human("Megan", 34),
            new Human("Jill", 67),
            new Human("Jack", 18),
            new Human("Max", 37)
    };

    public static Human[] getHumans() {
        return humans;
    }
}
