package com.rabeler.brokerage.repository;
import com.rabeler.brokerage.domain.SecurityPositions;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrokerageRepository extends MongoRepository<SecurityPositions, String> {
}
