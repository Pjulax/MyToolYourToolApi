package pl.polsl.io.mytoolyourtool.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.io.mytoolyourtool.api.dto.LoginDTO;
import pl.polsl.io.mytoolyourtool.api.dto.SignUpDTO;
import pl.polsl.io.mytoolyourtool.api.dto.UserDetailsDTO;
import pl.polsl.io.mytoolyourtool.domain.category.Category;
import pl.polsl.io.mytoolyourtool.domain.category.CategoryRepository;
import pl.polsl.io.mytoolyourtool.domain.offer.Offer;
import pl.polsl.io.mytoolyourtool.domain.offer.OfferRepository;
import pl.polsl.io.mytoolyourtool.domain.reservation.Reservation;
import pl.polsl.io.mytoolyourtool.domain.reservation.ReservationRepository;
import pl.polsl.io.mytoolyourtool.domain.review.Review;
import pl.polsl.io.mytoolyourtool.domain.review.ReviewRepository;
import pl.polsl.io.mytoolyourtool.utils.exception.ObjectExistsException;
import pl.polsl.io.mytoolyourtool.utils.security.jwt.JwtTokenProvider;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CategoryRepository categoryRepository;
    private final OfferRepository offerRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public String login(LoginDTO credentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
        return "Bearer " + jwtTokenProvider.createToken(credentials.getEmail(), userRepository.findByEmail(credentials.getEmail()).get().getRoles());
    }

    public void createUser(SignUpDTO signUpDTO) {
        if (null == signUpDTO.getEmail() || signUpDTO.getEmail().isEmpty()
                || !signUpDTO.getEmail().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
            throw new IllegalArgumentException("Email not provided or in bad format");

        if (null == signUpDTO.getPassword() || signUpDTO.getPassword().length() < 3)
            throw new IllegalArgumentException("Password has less than 3 characters");

        if (null == signUpDTO.getFirstName() || null == signUpDTO.getLastName()
                || signUpDTO.getFirstName().equals("") || signUpDTO.getLastName().equals(""))
            throw new IllegalArgumentException("You need to provide your firstname and lastname");

        if (userRepository.findByEmail(signUpDTO.getEmail()).isPresent())
            throw new ObjectExistsException("User with email '" + signUpDTO.getEmail() + "' already exists");

        LinkedList<Role> roles = new LinkedList<>();
        roles.add(Role.ROLE_CLIENT);

        User user = User.builder()
                .email(signUpDTO.getEmail())
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .firstName(signUpDTO.getFirstName())
                .lastName(signUpDTO.getLastName())
                .roles(roles)
                .build();
        userRepository.save(user);
    }

    public UserDetailsDTO getUserDetails() {
        User user = whoami();
        return new UserDetailsDTO(user.getEmail(), user.getFirstName(), user.getLastName());
    }

    public User whoami() {
        return search(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public User search(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User with email '" + email + "' doesn't exist"));
    }

    public void deleteUserAccount() {
        User user = whoami();

        List<Offer> offers = offerRepository.findOffersByLenderId(user.getId());
        for( Offer offer : offers)
            if(!offer.isReturned() && offer.isReservationChosen())
                throw new IllegalArgumentException("Cannot delete user with active lends");

        List<Reservation> reservations = reservationRepository.findMyReservations(user.getId()).orElse(List.of());
        for( Reservation reservation : reservations)
            if(reservation.isChosen() && !reservation.isFinished())
                throw new IllegalArgumentException("Cannot delete user with active borrows");
        reservationRepository.deleteAll(reservations);

        List<Reservation> reservationsOnMyOffers = reservationRepository.findMyLoans(user.getId()).orElse(List.of());
        if( !reservationsOnMyOffers.isEmpty() )
            reservationRepository.deleteAll(reservationsOnMyOffers);

        List<Review> reviewsFromMe = reviewRepository.findByReviewer_Id(user.getId()).orElse(List.of());
        User deleteDummyUser = userRepository.findByEmail("deleted@user.com").get();
        for( Review review : reviewsFromMe)
            review.setReviewer(deleteDummyUser);
        if(!reviewsFromMe.isEmpty())
            reviewRepository.saveAll(reviewsFromMe);

        List<Review> reviewsToMe = reviewRepository.findByReviewedUserId(user.getId()).orElse(List.of());
        if(!reviewsToMe.isEmpty())
            reviewRepository.deleteAll(reviewsToMe);

        List<Category> categories = categoryRepository.findAll();
        for( int j = 0; j < categories.size(); j++ ){
            List<Offer> innerOffers = categories.get(j).getOffers();
            for( int i = 0; i < innerOffers.size(); i++ ) {
                if( offers.contains(innerOffers.get(i)) ) {
                    innerOffers.remove(i);
                    i--;
                }
            }
            categories.get(j).setOffers(innerOffers);
        }
        categoryRepository.saveAll(categories);

        offerRepository.deleteAll(offers);

        userRepository.delete(user);

        /*
        Wszystkie wypożyczenia muszą być zakończone
        Wszystkie rezerwacje muszą być zakończone
        usuń cudze rezerwacje na twoich ofertach
        usuń oceny -> powiązane z userami -> ja reviewedUser -> delete, -> ja reviewer -> modyfikuj na usera "Deleted User"
        usuń ofertę z listy w kategorii -> category zawiera offers
        usuń oferty -> powiązane z userem
        usuń użytkownika, jego oferty, jego oceny, jego rezerwacje
        */
    }
}
