//package nyong.board.global.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import nyong.board.domain.member.service.LoginService;
//import nyong.board.global.login.filter.JsonUsernamePasswordAuthenticationFilter;
//import nyong.board.global.login.hadnler.LoginFailureHandler;
//import nyong.board.global.login.hadnler.LoginSuccessJWTProvideHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final LoginService loginService;
//    private final ObjectMapper objectMapper;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .formLogin().disable() // formLogin 비활성화
//                .httpBasic().disable() // (특정 리소스 접근시 USERNAME, PASSWORD 요구).disable
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/login", "/signUp", "/").permitAll()
//                .anyRequest().authenticated();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager() { // AuthenticationManager 등록
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();//DaoAuthenticationProvider 사용
//
////        PasswordEncoder로는 PasswordEncoderFactories.createDelegatingPasswordEncoder() 사용
//        provider.setPasswordEncoder(passwordEncoder());
//
//        provider.setUserDetailsService(loginService);
//        return new ProviderManager(provider);
//    }
//
//    @Bean
//    public LoginSuccessJWTProvideHandler loginSuccessJWTProvideHandler(){
//        return new LoginSuccessJWTProvideHandler();
//    }
//
//    @Bean
//    public LoginFailureHandler loginFailureHandler(){
//        return new LoginFailureHandler();
//    }
//
//    @Bean
//    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter(){
//        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter = new JsonUsernamePasswordAuthenticationFilter(objectMapper);
//
//        jsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
//        jsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessJWTProvideHandler());
//        jsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
//
//        return jsonUsernamePasswordLoginFilter;
//    }
//}
