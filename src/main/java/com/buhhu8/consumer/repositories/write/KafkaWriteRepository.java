package com.buhhu8.consumer.repositories.write;

import com.buhhu8.consumer.model.write.KafkaWriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaWriteRepository  extends JpaRepository<KafkaWriteEntity, String> {
}
