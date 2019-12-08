package pl.miasi.destinationlist;

import java.util.Objects;

public class Destination {

    Destination(String country, String city, String hotel) {
        this.country = country;
        this.city = city;
        this.hotel = hotel;
    }

    String country;
    String city;
    String hotel;

    @Override
    public int hashCode() {
        return Objects.hash(country, city, hotel);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Destination other = (Destination) obj;
        return Objects.equals(country, other.country) && Objects.equals(city, other.city) && Objects.equals(hotel, other.hotel);
    }
}
