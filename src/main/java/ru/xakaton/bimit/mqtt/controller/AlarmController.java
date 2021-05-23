package ru.xakaton.bimit.mqtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.xakaton.bimit.mqtt.service.WorldService;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

	@Autowired
	WorldService worldService;

	@GetMapping("/create")
	public String createAlarm() {
		worldService.createAlarm();
		return "OK";
	}

}
