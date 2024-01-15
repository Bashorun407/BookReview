package com.akinnova.BookReviewGrad;

import com.akinnova.BookReviewGrad.auditor.AuditorAwareImpl;
import com.akinnova.BookReviewGrad.service.userservice.IUserAuditCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebMvc
@Slf4j
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class BookReviewGradApplication {

	private final IUserAuditCheckService iUserAuditCheckService;

	public BookReviewGradApplication(IUserAuditCheckService iUserAuditCheckService) {
		this.iUserAuditCheckService = iUserAuditCheckService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BookReviewGradApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				WebMvcConfigurer.super.addCorsMappings(registry);
				registry
						.addMapping("/**")
						.allowedOrigins("*")
						.allowedHeaders("*")
						.allowedMethods("POST", "GET", "PUT", "PATCH", "OPTIONS");
			}
		};
	}

	@Bean
	public AuditorAware<String> auditorAware(){
		return new AuditorAwareImpl();
	}

	@Bean
	public ApplicationRunner init(){
		return args -> {

			iUserAuditCheckService.addUsers();
			System.out.println("Get all entities without having modified the database");
			iUserAuditCheckService.queryEntityHistory();
			Thread.sleep(10000);
			iUserAuditCheckService.updateUsers();
			System.out.println("Get all entities after modifying all entities in the database.");
			iUserAuditCheckService.queryEntityHistory();
		};
	}

//	@Bean
//	@Transactional
//	public CommandLineRunner commandLineRunner(UserRepository userRepository){
//		log.info("i am in the commandline runner");
//		return args -> {
//			userRepository.findAll().stream().peek(p-> {
//				p.setActiveStatus(true);
//				userRepository.save(p);
//				System.out.println("I have updated this guy " );
//			}).toList();
//		};
//	}

}
