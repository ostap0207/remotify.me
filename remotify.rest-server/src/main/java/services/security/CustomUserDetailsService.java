package services.security;

import java.util.Collection;

import db.UserDao;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import services.db.UserService;

/**
 * .
 * User: Ostap
 * Date: 2/18/14
 * Time: 12:36 AM
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getUserForEmail(username);
        if (user == null) {
            System.out.println("User null");
            throw new UsernameNotFoundException("Invalid username/password.");
        }

        System.out.println("User " + user.getEmail());
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        CustomUserDetails userDetails = new CustomUserDetails(user.getEmail(), user.getPassword(), authorities);
        userDetails.setAuthKey(user.getAuthKey());
        return userDetails;
    }

}