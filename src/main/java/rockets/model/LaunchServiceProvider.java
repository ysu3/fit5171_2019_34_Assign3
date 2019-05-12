package rockets.model;

import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Set;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class LaunchServiceProvider extends Entity {
    private String name;

    private int yearFounded;

    private String country;

    private String headquarters;

    private Set<Rocket> rockets;

    public LaunchServiceProvider(String name, int yearFounded, String country) {
        this.name = name;
        this.yearFounded = yearFounded;
        this.country = country;

        rockets = Sets.newLinkedHashSet();
    }

    public String getName() {
        return name;
    }

    public int getYearFounded() {
        return yearFounded;
    }

    public String getCountry() {
        return country;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public Set<Rocket> getRockets() {
        return rockets;
    }

    public void setHeadquarters(String headquarters) {
        notBlank(headquarters,"headquarters cannot be null or empty");
        this.headquarters = headquarters;
    }

    public void setRockets(Set<Rocket> rockets) {
        notNull(rockets,"rockets cannot be null or empty");
        this.rockets = rockets;
    }

    public void setName(String name) {
        notBlank(name, "name cannot be null or empty");
        if(!name.matches("^^[A-Za-z]+$"))
            throw new IllegalArgumentException("Invalid input");
        this.name = name;
    }

    public void setYearFounded(int yearFounded){
        notNull(yearFounded,"year cannot be null or empty");
        if (yearFounded < 1943 || yearFounded > 2019)
        {
            throw new IllegalArgumentException("Invalid Year");
        }
        this.yearFounded = yearFounded;
    }

    public void setCountry(String country){
        notBlank(country,"country cannot be null or empty");
        if(!country.matches("^^[A-Za-z]+$"))
                throw new IllegalArgumentException("Invalid input");
        this.country= country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaunchServiceProvider that = (LaunchServiceProvider) o;
        return yearFounded == that.yearFounded &&
                Objects.equals(name, that.name) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, yearFounded, country);
    }
}
