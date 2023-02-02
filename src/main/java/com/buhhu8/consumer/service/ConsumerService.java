package com.buhhu8.consumer.service;

import com.buhhu8.consumer.config.factory.MapperFactory;
import com.buhhu8.consumer.model.KafkaEntity;
import com.buhhu8.consumer.repositories.KafkaRepository;
import lombok.AllArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;


import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import java.io.*;
import java.util.List;

@Service
@AllArgsConstructor
public class ConsumerService {

    private final KafkaRepository kafkaRepository;
    private final MapperFactory mapperFactory;

    @KafkaListener(topics = "test")
    public void orderListener(String record) {
        try {
            var res = unmarshall(record);
            kafkaRepository.save(mapperFactory.mapTo(res, KafkaEntity.class));
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public generated.KafkaRequest unmarshall(String record) throws JAXBException, SAXException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("consumer/src/main/resources/schema1.xsd"));
        StringReader reader = new StringReader(record);
        JAXBContext jc = JAXBContext.newInstance(generated.KafkaRequest.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        unmarshaller.setSchema(schema);
        generated.KafkaRequest customer = (generated.KafkaRequest) unmarshaller.unmarshal(reader);
        return customer;
    }

    public List<generated.KafkaRequest> getAll() {
        return mapperFactory.mapListTo(kafkaRepository.findAll(), generated.KafkaRequest.class);

    }
}

