package space.cuongnh2k.core.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import space.cuongnh2k.core.enums.BusinessLogicEnum;
import space.cuongnh2k.core.exceptions.BusinessLogicException;
import space.cuongnh2k.rest.account.AccountRepository;
import space.cuongnh2k.rest.account.query.AccountRss;
import space.cuongnh2k.rest.account.query.GetAccountPrt;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDetailServiceUtil implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<AccountRss> listAccountRss = accountRepository.getAccount(new GetAccountPrt(email));
        if (CollectionUtils.isEmpty(listAccountRss)) {
            throw new BusinessLogicException(BusinessLogicEnum.BUSINESS_LOGIC_0002);
        }
        return new User(listAccountRss.get(0).getEmail(), listAccountRss.get(0).getPassword(), new ArrayList<>());
    }
}
