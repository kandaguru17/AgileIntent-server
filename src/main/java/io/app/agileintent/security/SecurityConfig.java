package io.app.agileintent.security;

import static io.app.agileintent.security.SecurityConstants.ACTIVATE_USER_ROUTE;
import static io.app.agileintent.security.SecurityConstants.AUTH_USER_ROUTE;
import static io.app.agileintent.security.SecurityConstants.H2_ROUTE;
import static io.app.agileintent.security.SecurityConstants.REGISTER_USER_ROUTE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private jwtAuthenticationEntryPoint unhandledAuthorization;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unhandledAuthorization).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// h2 database config
				.headers().frameOptions().sameOrigin().and().authorizeRequests()
				.antMatchers("/", 
						"/favicon.ico",
						"/**/*.png",
						"/**/*.gif", 
						"/**/*.svg", 
						"/**/*.jpg", 
						"/**/*.html",
						"/**/*.css", 
						"/**/*.js")
				.permitAll().antMatchers(AUTH_USER_ROUTE).permitAll().antMatchers(REGISTER_USER_ROUTE).permitAll()
				.antMatchers(ACTIVATE_USER_ROUTE).permitAll().antMatchers(H2_ROUTE).permitAll()
				.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
				.antMatchers("/actuator/*").permitAll().anyRequest().authenticated();

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
