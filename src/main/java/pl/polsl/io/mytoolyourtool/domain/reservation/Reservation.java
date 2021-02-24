package pl.polsl.io.mytoolyourtool.domain.reservation;

import lombok.*;
import pl.polsl.io.mytoolyourtool.domain.offer.Offer;
import pl.polsl.io.mytoolyourtool.domain.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User borrower;
    @ManyToOne
    private Offer offer;

    private boolean chosen;
    private boolean finished;
}
