package uni.unitravelplaner.dto.trip;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TripEditionDto {
    private Long id;
    private String title;
    private String description;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    @JsonCreator

    public TripEditionDto(@JsonProperty Long id, @JsonProperty String title, @JsonProperty String description,
                          @JsonProperty String startDate, @JsonProperty String endDate)
    {
        this.id = id;
        this.title = title;
        this.description = description;

        if (startDate != null && !startDate.isBlank()) {
            this.startDate = ZonedDateTime.parse(startDate);
        }

        if (endDate != null && !endDate.isBlank()) {
            this.endDate = ZonedDateTime.parse(endDate);
        }
    }
}
