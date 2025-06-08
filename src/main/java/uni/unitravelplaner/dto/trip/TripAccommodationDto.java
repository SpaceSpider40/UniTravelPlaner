package uni.unitravelplaner.dto.trip;

import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.ZonedDateTime;

@Data
public class TripAccommodationDto {
    public String name;
    public String description;
    public String address;
    public String city;
    public String country;
    public String link;

    public ZonedDateTime begin;
    public ZonedDateTime end;

    public Float price = 0f;
    public boolean isBreakfastIncluded = false;
}
