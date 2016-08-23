package hackro.tutorials.adaptermvp1.Models;

/**
 * Created by david on 22/08/16.
 */

public class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
