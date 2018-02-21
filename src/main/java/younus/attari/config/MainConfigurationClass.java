package younus.attari.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages={"younus.attari.config","younus.attari.controller"})
@EnableWebSecurity
@EnableWebMvc
public class MainConfigurationClass extends WebSecurityConfigurerAdapter implements WebApplicationInitializer {

	
	
	@Bean
	public ViewResolver getBean() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		// TODO Auto-generated method stub
//		web.ignoring().antMatchers("/resources/***");
//	}
//	

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers("/proxy","/ui","/scripts","/webjars/**", "/data","/css").permitAll()
		.antMatchers("/login","/logout","/home","/").hasAnyRole("USER","ADMIN")
		.and().formLogin().loginPage("/loginUrl").and().exceptionHandling().accessDeniedPage("/accessDenied")
		.and().logout().logoutUrl("/logoutUrl");
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN", "USER");
	}

	public void onStartup(ServletContext context) throws ServletException {

		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.setConfigLocation("younus.attari.config.MainConfigurationClass");

		ContextLoaderListener loadListener = new ContextLoaderListener(webContext);
		context.addListener(loadListener);
		
		ServletRegistration.Dynamic dispatcher = context.addServlet("Dispatcher", new DispatcherServlet(webContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		
		

	}

}
