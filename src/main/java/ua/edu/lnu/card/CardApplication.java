package ua.edu.lnu.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ua.edu.lnu.card.exceptions.exception.HttpError;

@SpringBootApplication
public class CardApplication {
	public static void main(String[] args) throws HttpError {
		SpringApplication.run(CardApplication.class, args);
	}
}
