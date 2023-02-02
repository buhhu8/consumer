package com.buhhu8.consumer.repositories.read;

import com.buhhu8.consumer.model.read.KafkaReadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaReadRepository extends JpaRepository<KafkaReadEntity, String> {
}
