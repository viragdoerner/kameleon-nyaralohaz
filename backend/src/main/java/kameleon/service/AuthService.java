package kameleon.service;

import exception.CustomMessageException;
import kameleon.dao.RoleRepository;
import kameleon.dao.UserRepository;
import kameleon.dao.VerificationTokenRepository;
import kameleon.dto.UserDTO;
import kameleon.model.auth.User;
import kameleon.model.auth.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;


    void deleteVerificationToken(User user){
        VerificationToken token = tokenRepository.findByUser(user);
        if(token != null) {
            tokenRepository.deleteById(token.getId());
        }
    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public String encodePassword(String pw){
        return encoder.encode(pw);
    }



    String getCurrentUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        throw new CustomMessageException("Nincs bejelentkezett felhasználó!");
    }
    public User getCurrentFullUser() {
        String currentUsername = getCurrentUsername();
        User user = userRepository.findByEmail(currentUsername).orElseThrow(() -> new RuntimeException("No user has been found with given email"));
        return user;
    }



}
