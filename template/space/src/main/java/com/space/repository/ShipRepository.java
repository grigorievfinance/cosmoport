package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findAllByNameContainingAndPlanetContainingAndProdDateBetweenAndSpeedGreaterThanEqualAndSpeedLessThanEqualAndCrewSizeGreaterThanEqualAndCrewSizeLessThanEqualAndRatingGreaterThanEqualAndRatingLessThanEqual(
            String name, String planet, Date afterDate, Date beforeDate, Double minSpeed, Double maxSpeed, Integer minCrewSize,
            Integer maxCrewSize, Double minRating, Double maxRating);

    List<Ship> findAllByisUsedAndNameContainingAndPlanetContainingAndProdDateBetweenAndSpeedGreaterThanEqualAndSpeedLessThanEqualAndCrewSizeGreaterThanEqualAndCrewSizeLessThanEqualAndRatingGreaterThanEqualAndRatingLessThanEqual(
            Boolean isUsed, String name, String planet, Date afterDate, Date beforeDate, Double minSpeed, Double maxSpeed,
            Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating);

    List<Ship> findAllByShipTypeAndNameContainingAndPlanetContainingAndProdDateBetweenAndSpeedGreaterThanEqualAndSpeedLessThanEqualAndCrewSizeGreaterThanEqualAndCrewSizeLessThanEqualAndRatingGreaterThanEqualAndRatingLessThanEqual(
            ShipType shipType, String name, String planet, Date afterDate, Date beforeDate, Double minSpeed, Double maxSpeed,
            Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating);

    List<Ship> findAllByisUsedAndShipTypeAndNameContainingAndPlanetContainingAndProdDateBetweenAndSpeedGreaterThanEqualAndSpeedLessThanEqualAndCrewSizeGreaterThanEqualAndCrewSizeLessThanEqualAndRatingGreaterThanEqualAndRatingLessThanEqual(
            Boolean isUsed, ShipType shipType, String name, String planet, Date afterDate, Date beforeDate, Double minSpeed,
            Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating);

    Ship save(Ship ship);
}
