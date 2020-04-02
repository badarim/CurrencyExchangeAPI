package com.exchange.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(path = "/exchangeService")
/*
 * //@CrossOrigin(origins ="http://localhost:4200")
 * 
 */

@CrossOrigin
public class ExchangeController {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/currentRate")
	private String getCurrentRate() {
		Date date = new Date();
		String currentModifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		String conversionCurrency = "GBP,USD,HKD";

		String resultApi = "https://api.ratesapi.io/api/" + currentModifiedDate + "?symbols=" + conversionCurrency;
		HttpEntity<String> entity = getEntity();
		ResponseEntity<String> response = restTemplate.exchange(resultApi, HttpMethod.GET, entity, String.class);
		return response.getBody();
	}

	@GetMapping("/historyRates")
	private List<String> getHistoryRates() {

		String conversionCurrency = "GBP,USD,HKD";
		String resultApi = "";
		Calendar now = Calendar.getInstance();

		HttpEntity<String> entity = getEntity();

		List<String> responseEntityList = new ArrayList<>();

		for (int i = 1; i <= 6; i++) {
			now = Calendar.getInstance();
			now.add(Calendar.MONTH, -i);
			String modDate = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
					+ now.get(Calendar.DATE);
			resultApi = "https://api.ratesapi.io/api/" + modDate + "?symbols=" + conversionCurrency;
			// RestTemplate restTemplate= restTemplate();

			ResponseEntity<String> response = restTemplate.exchange(resultApi, HttpMethod.GET, entity, String.class);
			responseEntityList.add(response.getBody());
		}

		return responseEntityList;

	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public HttpEntity<String> getEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		// RestTemplate restTemplate= restTemplate();
		return entity;
	}
}
