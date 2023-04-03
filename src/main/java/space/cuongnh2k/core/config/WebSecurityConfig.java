package space.cuongnh2k.core.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import space.cuongnh2k.core.filter.AuthFilter;
import space.cuongnh2k.core.utils.UserDetailServiceUtil;
import space.cuongnh2k.rest.account.AccountService;
import space.cuongnh2k.rest.account.dto.CreateAccountReq;

import java.util.Properties;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Log4j2
@EnableAsync
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final AuthFilter authFilter;
    private final UserDetailServiceUtil userDetailServiceUtil;

    @Value("${application.email.username}")
    private String EMAIL_SENDER_USERNAME;

    @Value("${application.email.password}")
    private String EMAIL_SENDER_PASSWORD;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .exposedHeaders("code")
                        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(EMAIL_SENDER_USERNAME);
        mailSender.setPassword(EMAIL_SENDER_PASSWORD);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailServiceUtil);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated());
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CommandLineRunner run(AccountService accountService) {
        return args -> {
            try {
                accountService.createAccount(CreateAccountReq.builder()
                        .email("cuongnh2k@gmail.com")
                        .password("123")
                        .firstName("Cường")
                        .lastName("Nguyễn")
                        .build());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        };
    }
}
