package ru.xakaton.bimit.mqtt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.dsl.context.IntegrationFlowContext.IntegrationFlowRegistration;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

@Configuration
public class MqttConfiguration {

	@Value("${mqttBroker}")
	private String mqttBroker;

	@Autowired
	IntegrationFlowContext flowContext;

	private static final Log LOGGER = LogFactory.getLog(MqttConfiguration.class);
	private static Map<String, IntegrationFlowRegistration> inFlows = new HashMap<String, IntegrationFlowRegistration>();

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { mqttBroker });
		factory.setConnectionOptions(options);
		return factory;
	}

	// publisher

	@Bean("mqttOutboundChannel")
	public MessageChannel mqttOutboundChannel() {
		return new DirectChannel();
	}

	@Bean
	public IntegrationFlow mqttOutFlow() {
		return IntegrationFlows.from("mqttOutboundChannel").handle(mqttOutbound()).get();
	}

	@Bean
	public MessageHandler mqttOutbound() {
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("siHomePublisher", mqttClientFactory());
		messageHandler.setAsync(true);
		return messageHandler;
	}

	@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
	public interface MqttGateway {
		void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
	}


}
