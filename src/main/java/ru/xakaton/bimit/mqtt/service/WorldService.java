package ru.xakaton.bimit.mqtt.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
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

	@Autowired
	ObjectMapper objectMapper;

	@Value("${bimitProject}")
	private String bimitProject;

	public void sendData(Sensor sensor, double value) {
		Message message = new Message();
		message.setSensorID(sensor.getId());
		message.setSensorName(sensor.getName());
		MessageTimestamp messageTimestamp = new MessageTimestamp();
		messageTimestamp.setTs(Timestamp.from(Instant.now()));
		MessageData messageData = new MessageData();
		messageData.setValue(String.format("%04.2f", value));
		messageData.setInfo("");
		messageTimestamp.setData(messageData);
		message.setTs(messageTimestamp);

		String topic = "/" + bimitProject + "/" + sensor.getId();

		String jsonResult = "";
		try {
			jsonResult = objectMapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		mqttGateway.sendToMqtt(jsonResult, topic);
	}

	@Scheduled(fixedDelay = 1000 / 120)
	public void askAndSend() {
		int gap = 3;
		for (Sensor sensor : myConfig.getSensors()) {
			Instant in = Instant.now();
			int minute = in.atZone(ZoneOffset.UTC).getMinute();
			int sec = in.atZone(ZoneOffset.UTC).getSecond();
			double value = Integer.parseInt(sensor.getValue()) + minute * 0.1;
			if (minute % gap == 0 && sec < gap * 2)
				value = Integer.parseInt(sensor.getValue()) * 1.5;
			sendData(sensor, value);
			gap += 2;
		}

	}

	public void createAlarm() {
		Sensor sensor = myConfig.getSensors().get(1);
		double value = Integer.parseInt(sensor.getValue()) * 2;
		sendData(sensor, value);
	}

}
