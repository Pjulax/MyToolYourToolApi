package pl.polsl.io.mytoolyourtool.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddReviewDTO {
    private final String opinion;
    private final Float rating;
    private final Long reviewedUserId;
}
