package ie.atu.sample.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookingRequest {

    @NotBlank(message = "Room name is required")
    private String RoomName;

    @NotBlank(message = "Organiser name cannot be blank")
    @Size(min = 2, max = 60, message = "Organiser name must be between 2 and 60 characters")
    private String OrganiserName;

    @Min(value = 1, message = "Count must be a positive whole number")
    private int count;
}
