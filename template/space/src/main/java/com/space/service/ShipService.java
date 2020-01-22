package com.space.service;


import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ShipService {

    @Autowired
    ShipRepository shipRepository;

    public List<Ship> find(String name, String planet, Long after, Long before, Double minSpeed, Double maxSpeed, Integer minCrewSize,
                           Integer maxCrewSize, Double minRating, Double maxRating, Boolean isUsed, ShipType shipType, ShipOrder order,
                           Integer pageNumber, Integer pageSize) {

        List<Ship> ships = null;

        if (order == null) {
            order = ShipOrder.ID;
        }

        Date afterDate = new Date(after);
        Date beforeDate = new Date(before - 3600001);

        if (shipType == null) {
            if (isUsed == null) {
                ships = findAllByParamsWithoutType(name, planet, afterDate, beforeDate, minSpeed, maxSpeed, minCrewSize,
                        maxCrewSize, minRating, maxRating, pageNumber, pageSize, order);
            } else {
                ships = findAllByParamsWithoutTypeWithUsed(isUsed, name, planet, afterDate, beforeDate, minSpeed, maxSpeed, minCrewSize,
                        maxCrewSize, minRating, maxRating, pageNumber, pageSize, order);
            }
        } else {
            if (isUsed == null) {
                ships = findAllByParamsWithType(shipType, name, planet, afterDate, beforeDate, minSpeed, maxSpeed, minCrewSize,
                        maxCrewSize, minRating, maxRating, pageNumber, pageSize, order);
            } else {
                ships = findAllByParamsWithTypeWithUsed(isUsed, shipType, name, planet, afterDate, beforeDate, minSpeed, maxSpeed, minCrewSize,
                        maxCrewSize, minRating, maxRating, pageNumber, pageSize, order);
            }
        }

        return ships;
    }

    List<Ship> shipParts(Integer pageNumber, Integer pageSize, ShipOrder order, List<Ship> allShips) {
        PagedListHolder<Ship> page = new PagedListHolder<>(allShips, new MutableSortDefinition(order.getFieldName(), true, true));
        page.resort();
        page.setPageSize(pageSize); // number of items per page
        page.setPage(pageNumber);      // set to first page
        return page.getPageList();
    }

    public List<Ship> findAllByParamsWithoutType(String name, String planet, Date afterDate, Date beforeDate, Double minSpeed,
                                                 Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating,
                                                 Double maxRating, Integer pageNumber, Integer pageSize, ShipOrder order) {
        List<Ship> allShips = shipRepository.findAllByNameContainingAndPlanetContainingAndProdDateBetweenAndSpeedGreaterThanEqualAndSpeedLessThanEqualAndCrewSizeGreaterThanEqualAndCrewSizeLessThanEqualAndRatingGreaterThanEqualAndRatingLessThanEqual
                (name, planet, afterDate, beforeDate, minSpeed, maxSpeed, minCrewSize,
                        maxCrewSize, minRating, maxRating);
        List<Ship> portion = shipParts(pageNumber, pageSize, order, allShips);
        return portion;
    }

    public List<Ship> findAllByParamsWithoutTypeWithUsed(Boolean isUsed, String name, String planet, Date afterDate, Date beforeDate,
                                                         Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize,
                                                         Double minRating, Double maxRating, Integer pageNumber, Integer pageSize, ShipOrder order) {
        List<Ship> allShips = shipRepository.findAllByisUsedAndNameContainingAndPlanetContainingAndProdDateBetweenAndSpeedGreaterThanEqualAndSpeedLessThanEqualAndCrewSizeGreaterThanEqualAndCrewSizeLessThanEqualAndRatingGreaterThanEqualAndRatingLessThanEqual
                (isUsed, name, planet, afterDate, beforeDate, minSpeed, maxSpeed, minCrewSize,
                        maxCrewSize, minRating, maxRating);
        List<Ship> portion = shipParts(pageNumber, pageSize, order, allShips);
        return portion;
    }

    public List<Ship> findAllByParamsWithType(ShipType shipType, String name, String planet, Date afterDate, Date beforeDate,
                                              Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize,
                                              Double minRating, Double maxRating, Integer pageNumber, Integer pageSize, ShipOrder order) {
        List<Ship> allShips = shipRepository.findAllByShipTypeAndNameContainingAndPlanetContainingAndProdDateBetweenAndSpeedGreaterThanEqualAndSpeedLessThanEqualAndCrewSizeGreaterThanEqualAndCrewSizeLessThanEqualAndRatingGreaterThanEqualAndRatingLessThanEqual
                (shipType, name, planet, afterDate, beforeDate, minSpeed, maxSpeed, minCrewSize,
                        maxCrewSize, minRating, maxRating);
        List<Ship> portion = shipParts(pageNumber, pageSize, order, allShips);
        return portion;
    }

    public List<Ship> findAllByParamsWithTypeWithUsed(Boolean isUsed, ShipType shipType, String name, String planet, Date afterDate,
                                                      Date beforeDate, Double minSpeed, Double maxSpeed, Integer minCrewSize,
                                                      Integer maxCrewSize, Double minRating, Double maxRating, Integer pageNumber,
                                                      Integer pageSize, ShipOrder order) {
        List<Ship> allShips = shipRepository.findAllByisUsedAndShipTypeAndNameContainingAndPlanetContainingAndProdDateBetweenAndSpeedGreaterThanEqualAndSpeedLessThanEqualAndCrewSizeGreaterThanEqualAndCrewSizeLessThanEqualAndRatingGreaterThanEqualAndRatingLessThanEqual
                (isUsed, shipType, name, planet, afterDate, beforeDate, minSpeed, maxSpeed, minCrewSize,
                        maxCrewSize, minRating, maxRating);
        List<Ship> portion = shipParts(pageNumber, pageSize, order, allShips);
        return portion;
    }

    public Integer shipCount(String name, String planet, Long after, Long before, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, Boolean isUsed, ShipType shipType) {
        Integer count;
        Date afterDate = new Date(after);
        Date beforeDate = new Date(before - 3600001);

        if (shipType == null) {
            if (isUsed == null) {
                count = shipRepository.findAllByNameContainingAndPlanetContainingAndProdDateBetweenAndSpeedGreaterThanEqualAndSpeedLessThanEqualAndCrewSizeGreaterThanEqualAndCrewSizeLessThanEqualAndRatingGreaterThanEqualAndRatingLessThanEqual(
                        name, planet, afterDate, beforeDate, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating).size();
            } else {
                count = shipRepository.findAllByisUsedAndNameContainingAndPlanetContainingAndProdDateBetweenAndSpeedGreaterThanEqualAndSpeedLessThanEqualAndCrewSizeGreaterThanEqualAndCrewSizeLessThanEqualAndRatingGreaterThanEqualAndRatingLessThanEqual(
                        isUsed, name, planet, afterDate, beforeDate, minSpeed, maxSpeed, minCrewSize,
                        maxCrewSize, minRating, maxRating).size();
            }
        } else {
            if (isUsed == null) {
                count = shipRepository.findAllByShipTypeAndNameContainingAndPlanetContainingAndProdDateBetweenAndSpeedGreaterThanEqualAndSpeedLessThanEqualAndCrewSizeGreaterThanEqualAndCrewSizeLessThanEqualAndRatingGreaterThanEqualAndRatingLessThanEqual(
                        shipType, name, planet, afterDate, beforeDate, minSpeed, maxSpeed, minCrewSize,
                        maxCrewSize, minRating, maxRating).size();
            } else {
                count = shipRepository.findAllByisUsedAndShipTypeAndNameContainingAndPlanetContainingAndProdDateBetweenAndSpeedGreaterThanEqualAndSpeedLessThanEqualAndCrewSizeGreaterThanEqualAndCrewSizeLessThanEqualAndRatingGreaterThanEqualAndRatingLessThanEqual(
                        isUsed, shipType, name, planet, afterDate, beforeDate, minSpeed, maxSpeed, minCrewSize,
                        maxCrewSize, minRating, maxRating).size();
            }
        }
        return count;
    }

    public ResponseEntity<Ship> save(Ship ship) {

        Integer prodYear = null;
        if (ship.getProdDate() != null && ship.getProdDate().getTime() > 0) {
            prodYear = getYear(ship);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }

        if (ship.getName() == null ||
                ship.getPlanet() == null ||
                ship.getShipType() == null ||
                ship.getSpeed() == null ||
                ship.getCrewSize() == null ||
                ship.getName().equals("") ||
                ship.getPlanet().equals("") ||
                ship.getName().length() > 50 ||
                ship.getPlanet().length() > 50 ||
                ship.getSpeed() < 0.01 ||
                ship.getSpeed() > 0.99 ||
                ship.getCrewSize() < 1 ||
                ship.getCrewSize() > 9999 ||
                prodYear < 2800 ||
                prodYear > 3019) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Double rating = calculateRating(ship);
        Ship newShip = shipRepository.save(new Ship(ship.getName(), ship.getPlanet(), ship.getShipType(), ship.getProdDate(), ship.getUsed(), ship.getSpeed(), ship.getCrewSize(), rating));
        return new ResponseEntity<>(newShip, HttpStatus.OK);
    }

    public ResponseEntity<Ship> getById(Long id) {
        if (!validateId(id)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Ship> optional = shipRepository.findById(id);
        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Ship> updateById(Long id, Ship ship) {

        if (!validateId(id)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Ship> optional = shipRepository.findById(id);
        if (!optional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Ship foundedShip = optional.get();

            if (ship.getName() == null & ship.getPlanet() == null & ship.getShipType() == null & ship.getProdDate() == null &
                    ship.getUsed() == null & ship.getSpeed() == null & ship.getCrewSize() == null) {
                return new ResponseEntity<>(foundedShip, HttpStatus.OK);
            }

            if (ship.getName() != null && ship.getName().length() <= 50 && ship.getName().length() > 0) {
                foundedShip.setName(ship.getName());
            }
            if (ship.getName() != null && ship.getName().equals("")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            if (ship.getPlanet() != null && ship.getPlanet().length() <= 50 && ship.getPlanet().length() > 0) {
                foundedShip.setPlanet(ship.getPlanet());
            }

            if (ship.getShipType() != null) {
                foundedShip.setShipType(ship.getShipType());
            }

            if (ship.getProdDate() != null && ship.getProdDate().getTime() > 0) {
                Integer prodYear = getYear(ship);
                if (prodYear >= 2800 && prodYear <= 3019) {
                    foundedShip.setProdDate(ship.getProdDate());
                }
            }
            if (ship.getProdDate() != null && ship.getProdDate().getTime() < 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            if (ship.getUsed() != null) {
                foundedShip.setUsed(ship.getUsed());
            }

            if (ship.getSpeed() != null && ship.getSpeed() >= 0.01 && ship.getSpeed() <= 0.99) {
                foundedShip.setSpeed(ship.getSpeed());
            }

            if(ship.getCrewSize() != null && ship.getCrewSize() >= 1 && ship.getCrewSize() <= 9999) {
                foundedShip.setCrewSize(ship.getCrewSize());
            }
            if (ship.getCrewSize() != null && (ship.getCrewSize() > 9999  || ship.getCrewSize() < 1)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Double rating = calculateRating(foundedShip);
            foundedShip.setRating(rating);
            Ship updatedShip = shipRepository.save(foundedShip);

            return new ResponseEntity<>(updatedShip, HttpStatus.OK);
        }
    }

    public ResponseEntity<Ship> deleteById(Long id) {
        if (!validateId(id)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Optional<Ship> optional = shipRepository.findById(id);
        if (!optional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            shipRepository.deleteById(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Double calculateRating(Ship ship) {
        Double k = ship.getUsed() ? 0.5 : 1;
        return new BigDecimal((80 * ship.getSpeed() * k) / (3019 - getYear(ship) + 1)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private Integer getYear(Ship ship) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(ship.getProdDate().getTime());
        return calendar.get(Calendar.YEAR);
    }

    private boolean validateId(Long id) {
        String idString = String.valueOf(id);
        for (int i = 0; i < idString.length(); i++) {
            if (!Character.isDigit(idString.charAt(i))) {
                return false;
            }
        }
        if (id <= 0 ) return false;
        return true;
    }
}
