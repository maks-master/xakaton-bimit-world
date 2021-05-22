package ru.xakaton.bimit.mqtt.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.xakaton.bimit.YAMLConfig;
import ru.xakaton.bimit.YAMLConfig.Sensor;
import ru.xakaton.bimit.model.Message;
import ru.xakaton.bimit.model.MessageData;
import ru.xakaton.bimit.model.MessageTimestamp;
import ru.xakaton.bimit.mqtt.MqttConfiguration;

@Service
public class WorldService {
	private final Logger log = LoggerFactory.getLogger(WorldService.class);

	@Autowired
	MqttConfiguration mqttConfiguration;

	@Autowired
	private YAMLConfig myConfig;

	@Autowired
	MqttConfiguration.MqttGateway mqttGateway;

	@Value("${bimitProject}")
	private String bimitProject;

	@Scheduled(fixedDelay = 1000)
	public void askAndSend() {
		for (Sensor sensor : myConfig.getSensors()) {
			Random rnd = new Random();
			double temp = 20 + rnd.nextDouble() * 20.0;

			Message message = new Message();
			message.setSensorID(sensor.getId());
			message.setSensorName(sensor.getName());
			MessageTimestamp messageTimestamp = new MessageTimestamp();
			messageTimestamp.setTs(Timestamp.from(Instant.now()));
			MessageData messageData= new MessageData();
			messageData.setValue(String.format("%04.2f", temp));
			messageTimestamp.setData(messageData);
			message.setTs(messageTimestamp);
			
			String topic = "/" + bimitProject + "/" + sensor.getId();

			log.info(sensor.toString());

			ObjectMapper mapper = new ObjectMapper();
			String jsonResult = "";
			try {
				jsonResult = mapper.writeValueAsString(message);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			mqttGateway.sendToMqtt(jsonResult, topic);
		}

	}

}
