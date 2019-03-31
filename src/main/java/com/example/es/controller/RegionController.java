package com.example.es.controller;

import com.example.es.Service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "regionController")
@RequestMapping(value = "/region")
public class RegionController {

    @Autowired
    RegionService regionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Object query(@RequestParam(value = "id", required = false) Long id,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "longitude", required = false) Double longitude,
                                           @RequestParam(value = "latitude", required = false) Double latitude,
                                           @RequestParam(value = "radiusKm", required = false) Double radiusKm) {
        return regionService.query(id, name, longitude, latitude, radiusKm);
    }
}
