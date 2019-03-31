package com.rabeler.brokerage.repository;
import com.rabeler.brokerage.domain.PositionSummary;
import com.rabeler.brokerage.domain.SecurityPositions;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrokerageRepository extends MongoRepository<SecurityPositions, String> {
    PositionSummary findByid(ObjectId _id);
}
