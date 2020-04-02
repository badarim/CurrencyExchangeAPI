package com.exchange.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.exchange.controller.ExchangeController;

@ComponentScan("com.exchange")

@SpringBootApplication

public class CurrencyExchangeApiApplication {

	public static void main(String[] args) {
		//System.out.println("call exchange");
		SpringApplication.run(CurrencyExchangeApiApplication.class, args);
	}

}
