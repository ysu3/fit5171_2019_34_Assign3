package rockets.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.CompositeIndex;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;
import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

@NodeEntity
@CompositeIndex(properties = {"name", "country", "manufacturer"}, unique = true)
public class Rocket extends Entity {
    @Property(name="name")
    private String name;

    @Property(name="country")
    private String country;

    @Relationship(type = "MANUFACTURES", direction = INCOMING)
    private String manufacturer;

    @Property(name="massToLEO")
    private String massToLEO;

    @Property(name="massToGTO")
    private String massToGTO;

    @Property(name="massToOther")
    private String massToOther;

    @Property(name="firstYearFlight")
    private int firstYearFlight;

    @Property(name="lastYearFlight")
    private int latestYearFlight;

    @Relationship(type = "PROVIDES", direction = OUTGOING)
    @JsonIgnore
    private Set<Launch> launches;

    public Rocket() {
        super();
    }

    /**
     * All parameters shouldn't be null.
     *
     * @param name
     * @param country
     * @param manufacturer
     */
    public Rocket(String name, String country, String manufacturer) {
        notNull(name);
        notNull(country);
        notNull(manufacturer);

        this.name = name;
        this.country = country;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getMassToLEO() {
        return massToLEO;
    }

    public String getMassToGTO() {
        return massToGTO;
    }

    public String getMassToOther() {
        return massToOther;
    }

    public void setName(String name){
        notBlank(name,"name cannot be null or empty");
        if(isLetterOrNumber(name))
        {
            this.name = name;
        }
        else{
            throw new IllegalArgumentException("wrong name format");
        }
        this.name = name;
    }

    public void setCountry(String country){
        notBlank(country,"country cannot be null or empty");
        if(countryCheck(country))
        {
            this.country = country;
        }
        else{
            throw new IllegalArgumentException("wrong name format");
        }
        this.country = country;
    }


    public void setManufacturer(String manufacturerName) {
        notNull(manufacturerName,"manufacturer name cannot be null or empty");
        this.manufacturer = manufacturerName;
    }

    private static boolean isLetterOrNumber(String string) {
        boolean flag = false;
        for (int i = 0; i < string.length(); i++) {
            if (Character.isLetterOrDigit(string.charAt(i))) {
                flag = true;
            } else {
                flag = false;
                return flag;
            }
        }
        return flag;
    }

    private static boolean countryCheck(String country){
        boolean flag = false;
        for(int i = 0; i < country.length(); i++){
            if (Character.isLetter(country.charAt(i))) {
                flag = true;
            } else {
                flag = false;
                return flag;
            }
        }
        return flag;

    }


    public void setMassToLEO(String massToLEO) {
        notNull(massToLEO, "massToLEO cannot be null or empty");
        int i = Integer.parseInt(massToLEO);

        if (i < 0)
            throw new IllegalArgumentException("mass cannot be smaller then 0");
        else if (i > 140000)
            throw new IllegalArgumentException("MassToLEO cannot be larger than 200000");

        this.massToLEO = massToLEO;
    }

    public void setMassToGTO(String massToGTO) {
        notNull(massToGTO, "massToGTO cannot be null or empty");
        int i = Integer.parseInt(massToGTO);

        if (i < 0)
            throw new IllegalArgumentException("MassToGTO cannot be smaller then 0");
        else if (i > 66000)
            throw new IllegalArgumentException("MassToGTO cannot be larger than 200000");
        this.massToGTO = massToGTO;

    }

    public void setMassToOther(String massToOther) {
        notNull(massToOther, "massToOther cannot be null or empty");
        int i = Integer.parseInt(massToOther);

        if (i < 0)
            throw new IllegalArgumentException("MassToOther cannot be smaller then 0");
        else if (i > 50000)
            throw new IllegalArgumentException("MassToOther cannot be larger than 200000");
        this.massToOther = massToOther;
    }

    public void setFirstYearFlight(int firstYearFlight) {
        this.firstYearFlight = firstYearFlight;
    }

    public void setLatestYearFlight(int latestYearFlight) {
        this.latestYearFlight = latestYearFlight;
    }

    public Set<Launch> getLaunches() {
        return launches;
    }

    public void setLaunches(Set<Launch> launches) {
        this.launches = launches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rocket rocket = (Rocket) o;
        return Objects.equals(name, rocket.name) &&
                Objects.equals(country, rocket.country) &&
                Objects.equals(manufacturer, rocket.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, manufacturer);
    }

    @Override
    public String toString() {
        return "Rocket{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", massToLEO='" + massToLEO + '\'' +
                ", massToGTO='" + massToGTO + '\'' +
                ", massToOther='" + massToOther + '\'' +
                '}';
    }
}
