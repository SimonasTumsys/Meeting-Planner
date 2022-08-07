package lt.bit.meetings.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable();
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                    .loginPage("/login")
//                    .usernameParameter("email")
//                    .passwordParameter("password")
//                    .permitAll()
//                    .defaultSuccessUrl("/dashboard", true)
//                .and()
//                .rememberMe()
//                    .rememberMeParameter("remember-me")
//
//                .and()
//                .authorizeRequests()
//                .and()
//                .logout()
//                    .logoutUrl("/logout")
////                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
//                    .clearAuthentication(true)
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID", "remember-me")
//                    .logoutSuccessUrl("/login").permitAll();

        return http.build();
    }
}

//    public SecurityConfiguration(AuthSuccessHandler authSuccessHandler,
//                                     AttendeeDetailsService attendeeDetailsService,
//                                     @Value("${jwt.secret}") String secret) {
//        this.authSuccessHandler = authSuccessHandler;
//        this.attendeeDetailsService = attendeeDetailsService;
//        this.secret = secret;
//    }

//    @Bean
//    public JsonObjectAuthenticationFilter authenticationFilter() throws Exception{
//        JsonObjectAuthenticationFilter filter = new JsonObjectAuthenticationFilter();
//        filter.setAuthenticationSuccessHandler(authSuccessHandler);
//        filter.setAuthenticationManager(authenticationManager);
//        return filter;
//    }

//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    private final AuthSuccessHandler authSuccessHandler;
//    private final AttendeeDetailsService attendeeDetailsService;
//    private final String secret;

//                .cors()
//                .and()
//                .csrf().disable()
//                .authorizeHttpRequests((auth) -> {
//                            try {
//                                auth
//                                        .antMatchers("/").permitAll()
//                                        .anyRequest()
//                                        .authenticated()
//                                        .and()
//                                        .sessionManagement()
//                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                                        .and()
//                                        .addFilter(authenticationFilter())
////                                        .addFilter(new JwtAuthorizationFilter(authenticationManager, attendeeDetailsService, secret))
//                                        .exceptionHandling()
//                                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
//
//
//                            } catch (Exception e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                        )
//                .httpBasic(Customizer.withDefaults());
//
















