package space.cuongnh2k.core.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.rest.account.AccountRepository;
import space.cuongnh2k.rest.account.query.GetAccountRss;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class UserDetailServiceUtil implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        GetAccountRss rss = accountRepository.getOneByEmail(email);
        if (rss == null) {
            throw new BusinessLogicException();
        }
        return new User(rss.getEmail(), rss.getPassword(), new ArrayList<>());
    }
}
