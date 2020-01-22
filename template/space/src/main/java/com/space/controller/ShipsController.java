package com.space.controller;


import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/rest")
public class ShipsController {

    @Autowired
    private ShipService shipService;

    @GetMapping(value = "/ships/count", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Integer> getShipsCount(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "planet", required = false, defaultValue = "") String planet,
            @RequestParam(value = "after", required = false, defaultValue = "26192235600000") Long after,
            @RequestParam(value = "before", required = false, defaultValue = "33134648400000") Long before,
            @RequestParam(value = "minSpeed", required = false, defaultValue = "0.01") Double minSpeed,
            @RequestParam(value = "maxSpeed", required = false, defaultValue = "0.99") Double maxSpeed,
            @RequestParam(value = "minCrewSize", required = false, defaultValue = "1") Integer minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false, defaultValue = "9999") Integer maxCrewSize,
            @RequestParam(value = "minRating", required = false, defaultValue = "0.0") Double minRating,
            @RequestParam(value = "maxRating", required = false, defaultValue = "80.0") Double maxRating,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
            @RequestParam(value = "shipType", required = false) ShipType shipType
    ) {
        Integer count = shipService.shipCount(name, planet, after, before, minSpeed, maxSpeed, minCrewSize, maxCrewSize,
                minRating, maxRating, isUsed, shipType);

        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping(value = "/ships", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Ship>> getShipsWParams(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "planet", required = false, defaultValue = "") String planet,
            @RequestParam(value = "after", required = false, defaultValue = "26192235600000") Long after,
            @RequestParam(value = "before", required = false, defaultValue = "33134648400000") Long before,
            @RequestParam(value = "minSpeed", required = false, defaultValue = "0.01") Double minSpeed,
            @RequestParam(value = "maxSpeed", required = false, defaultValue = "0.99") Double maxSpeed,
            @RequestParam(value = "minCrewSize", required = false, defaultValue = "1") Integer minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false, defaultValue = "9999") Integer maxCrewSize,
            @RequestParam(value = "minRating", required = false, defaultValue = "0.0") Double minRating,
            @RequestParam(value = "maxRating", required = false, defaultValue = "80.0") Double maxRating,
            @RequestParam(value = "order", required = false) ShipOrder order,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
            @RequestParam(value = "shipType", required = false) ShipType shipType) {

        List<Ship> ships = shipService.find(name, planet, after, before, minSpeed, maxSpeed, minCrewSize,
                maxCrewSize, minRating, maxRating, isUsed, shipType, order, pageNumber, pageSize);

        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @PostMapping(value = "/ships", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> create(@RequestBody Ship ship) {

        return shipService.save(ship);

    }

    @GetMapping(value = "/ships/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> getShip(@PathVariable("id") Long id) {
        return shipService.getById(id);
    }

    @PostMapping(value = "/ships/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> updateShip(@PathVariable("id") Long id, @RequestBody Ship ship) {
        return shipService.updateById(id, ship);
    }

    @DeleteMapping(value = "/ships/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> deleteShip(@PathVariable("id") Long id) {
        return shipService.deleteById(id);
    }

}
