package ru.xakaton.bimit.mqtt.service;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.xakaton.bimit.mqtt.MqttConfiguration;

@Service
public class WorldService {
	private final Logger log = LoggerFactory.getLogger(WorldService.class);

	@Autowired
	MqttConfiguration mqttConfiguration;

	@Autowired
	MqttConfiguration.MqttGateway mqttGateway;

	@Value("${bimitProject}")
	private String bimitProject;

	@Scheduled(fixedDelay = 1000)
	public void askAndSend() {
		Random rnd = new Random();
		double temp = 80 + rnd.nextDouble() * 20.0;

		String value = String.format("%04.2f", temp);
		String topic = "/" + bimitProject + "/temp";
		mqttGateway.sendToMqtt(value, topic);
	}

}
