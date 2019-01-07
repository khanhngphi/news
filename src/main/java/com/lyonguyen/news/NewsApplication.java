package com.lyonguyen.news;

import com.lyonguyen.news.services.SecurityService;
import com.lyonguyen.news.services.SecurityServiceImpl;
import com.lyonguyen.news.utils.Converter;
import com.lyonguyen.news.utils.Random;
import com.lyonguyen.news.utils.Url;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NewsApplication {

	@Bean
	public Converter converter() {
		return new Converter();
	}

	@Bean
	public Url url() { return new Url(); }

	@Bean
    public Random random() { return new Random(); }

	public static void main(String[] args) {
		SpringApplication.run(NewsApplication.class, args);
	}
}
