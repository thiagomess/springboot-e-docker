package br.com.erudio.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.erudio.security.JWTAuthenticationFilter;
import br.com.erudio.security.JWTAuthorizationFilter;
import br.com.erudio.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //habilita segurança com roles especificas em metodos Especificos usando 
//a anotação @PreAuthorize("hasAnyRole('ADMIN')")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//Recupera o profile ativo
	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;

//	private static final String[] PUBLIC_MATCHERS = {
//			"/h2-console/**" 
//			};
	
//	private static final String[] PUBLIC_MATCHERS_GET = {
//			"/produtos/**", 
//			"/estados/**",
//			"/categorias/**" 
//			};
	private static final String[] PUBLIC_MATCHERS_POST = {
//			"/clientes",
			"/auth/**"
			};

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
		"/swagger-ui.html", "/webjars/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
			//Se tiver no perfil de Test, ira liberar acesso ao H2
		}

		http.cors() // ativa o bean CorsConfigurationSource
				.and().csrf().disable(); // e Desativa o csrf pois a aplicação nao armazena sessao
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll() //Permite apenas os metodos POST do Array
			.anyRequest().authenticated();
//			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() //Permite apenas os metodos GET do Array
//			.antMatchers(PUBLIC_MATCHERS).permitAll()
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil)); //Adiciona o filter para autenticar egerar o token
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService)); //Adiciona o filter para validar o token e autorizar
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// Pra assegurar que nao ira
																						// criar sessao

	}

	// Permite acesso aos endpoints por multiplas fontes com as configurações
	// basicas
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
	
	//Poderá injetar ele no sistema
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//Define quem é o usuarioService e o encoder
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
