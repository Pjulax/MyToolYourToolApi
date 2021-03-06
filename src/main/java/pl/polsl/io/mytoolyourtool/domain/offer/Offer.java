package pl.polsl.io.mytoolyourtool.domain.offer;

import lombok.*;

import pl.polsl.io.mytoolyourtool.domain.user.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Offer {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String toolName;
    private String description;
    @Enumerated(EnumType.STRING)
    private ToolQuality toolQuality;
    @ManyToOne
    private User lender;

    private boolean reservationChosen;
    private boolean returned;
    private boolean lenderReviewed;
    private boolean borrowerReviewed;
}
