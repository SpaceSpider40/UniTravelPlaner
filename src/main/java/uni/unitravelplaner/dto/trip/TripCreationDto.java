package uni.unitravelplaner.dto.trip;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class TripCreationDto {
    public String title = "";
    public String description = "";
    public Long organizerId;

    public ZonedDateTime startDate;
    public ZonedDateTime endDate;

    @JsonCreator
    public TripCreationDto(
            @JsonProperty String title,
            @JsonProperty String description,
            @JsonProperty Long organizerId,
            @JsonProperty String startDate,
            @JsonProperty String endDate)
    {
        this.title = title;
        this.description = description;
        this.organizerId = organizerId;
        this.startDate = ZonedDateTime.parse(startDate);
        this.endDate = ZonedDateTime.parse(endDate);
    }
}
