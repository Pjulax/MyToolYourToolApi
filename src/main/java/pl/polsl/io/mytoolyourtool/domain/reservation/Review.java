package pl.polsl.io.mytoolyourtool.domain.reservation;

import lombok.*;
import pl.polsl.io.mytoolyourtool.domain.user.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String opinion;
    private Float rating;
    @ManyToOne
    private User reviewer;
    @ManyToOne
    private User reviewedUser;
}
