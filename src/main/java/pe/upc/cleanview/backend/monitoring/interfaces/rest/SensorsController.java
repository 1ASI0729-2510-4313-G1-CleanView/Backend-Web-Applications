package com.acme.center.platform.monitoring.interfaces.rest;

import com.acme.center.platform.monitoring.domain.model.commands.DeleteSensorCommand;
import com.acme.center.platform.monitoring.domain.model.queries.GetAllSensorsQuery;
import com.acme.center.platform.monitoring.domain.model.queries.GetSensorByIdQuery;
import com.acme.center.platform.monitoring.domain.services.SensorCommandService;
import com.acme.center.platform.monitoring.domain.services.SensorQueryService;
import com.acme.center.platform.monitoring.interfaces.rest.resources.CreateSensorResource;
import com.acme.center.platform.monitoring.interfaces.rest.resources.SensorResource;
import com.acme.center.platform.monitoring.interfaces.rest.resources.UpdateSensorResource;
import com.acme.center.platform.monitoring.interfaces.rest.transform.CreateSensorCommandFromResourceAssembler;
import com.acme.center.platform.monitoring.interfaces.rest.transform.SensorResourceFromEntityAssembler;
import com.acme.center.platform.monitoring.interfaces.rest.transform.UpdateSensorCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/sensors", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Sensors", description = "Available Sensor Endpoints")
public class SensorsController {

    private final SensorCommandService sensorCommandService;
    private final SensorQueryService sensorQueryService;


    public SensorsController(SensorCommandService sensorCommandService,
                             SensorQueryService sensorQueryService) {
        this.sensorCommandService = sensorCommandService;
        this.sensorQueryService = sensorQueryService;
    }

    @PostMapping
    @Operation(summary = "Create Sensor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sensor created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<SensorResource> createSensor(@RequestBody CreateSensorResource resource) {
        var createSensorCommand = CreateSensorCommandFromResourceAssembler.toCommandFromResource(resource);
        var sensor = sensorCommandService.handle(createSensorCommand);
        if (sensor.isEmpty()) return ResponseEntity.badRequest().build();
        var sensorResource = SensorResourceFromEntityAssembler.toResourceFromEntity(sensor.get());
        return new ResponseEntity<>(sensorResource, HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    @Operation(summary = "Update Sensor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor updated"),
            @ApiResponse(responseCode = "404", description = "Bad request")
    })
    public ResponseEntity<SensorResource> updateSensor(@PathVariable Long id, @RequestBody UpdateSensorResource resource) {
        var updateSensorCommand = UpdateSensorCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var sensor = sensorCommandService.handle(updateSensorCommand);
        if (sensor.isEmpty()) return ResponseEntity.badRequest().build();
        var sensorResource = SensorResourceFromEntityAssembler.toResourceFromEntity(sensor.get());
        return new ResponseEntity<>(sensorResource, HttpStatus.OK);
    }




    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Sensor")
    public ResponseEntity<SensorResource> deleteSensor(@PathVariable Long id) {
        var deleteSensorCommand = new DeleteSensorCommand(id);
        sensorCommandService.handle(deleteSensorCommand);
        return ResponseEntity.noContent().build();
    }



    @GetMapping
    @Operation(summary = "Get all sensors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor Found"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<List<SensorResource>> getAllSensors() {
        var sensor = sensorQueryService.handle(new GetAllSensorsQuery());
        if (sensor.isEmpty()) return ResponseEntity.badRequest().build();
        var sensorResource = sensor.stream()
                .map(SensorResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return new ResponseEntity<>(sensorResource, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    @Operation(summary = "Get sensor by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor Found"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<SensorResource> getSensor(@PathVariable Long id) {
        var sensor = sensorQueryService.handle(new GetSensorByIdQuery(id));
        if (sensor.isEmpty()) return ResponseEntity.badRequest().build();
        var sensorResource = SensorResourceFromEntityAssembler.toResourceFromEntity(sensor.get());
        return new ResponseEntity<>(sensorResource, HttpStatus.OK);
    }


}
