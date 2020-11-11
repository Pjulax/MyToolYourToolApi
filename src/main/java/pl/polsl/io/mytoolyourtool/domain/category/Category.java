package pl.polsl.io.mytoolyourtool.domain.category;

import lombok.*;
import pl.polsl.io.mytoolyourtool.domain.offer.Offer;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany
    private List<Offer> offers;


}
