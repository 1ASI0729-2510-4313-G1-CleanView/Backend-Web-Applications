package pe.upc.cleanview.backend.monitoring.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetWasteByIdQuery;
import pe.upc.cleanview.backend.monitoring.domain.services.WasteCommandService;
import pe.upc.cleanview.backend.monitoring.domain.services.WasteQueryService;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.CreateWasteResource;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.WasteResource;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.transform.CreateWasteCommandFromResourceAssembler;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.transform.WasteResourceFromEntityAssembler;

@RestController
@RequestMapping(value = "/api/v1/wastes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Wastes", description = "Available Waste Endpoints")
public class WastesController {

    private final WasteCommandService wasteCommandService;
    private final WasteQueryService wasteQueryService;

    public WastesController(WasteCommandService wasteCommandService,
                            WasteQueryService wasteQueryService) {
        this.wasteCommandService = wasteCommandService;
        this.wasteQueryService = wasteQueryService;
    }

    @PostMapping
    @Operation(summary = "Create Waste")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Waste created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<WasteResource> createWaste(@RequestBody CreateWasteResource resource) {
        var createWasteCommand = CreateWasteCommandFromResourceAssembler.toCommandFromResource(resource);
        var waste = wasteCommandService.handle(createWasteCommand);
        if (waste.isEmpty()) return ResponseEntity.badRequest().build();
        var wasteResource = WasteResourceFromEntityAssembler.toResourceFromEntity(waste.get());
        return new ResponseEntity<>(wasteResource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Waste by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waste Found"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<WasteResource> getWaste(@PathVariable Long id) {
        var waste = wasteQueryService.handle(new GetWasteByIdQuery(id));
        if (waste.isEmpty()) return ResponseEntity.badRequest().build();
        var wasteResource = WasteResourceFromEntityAssembler.toResourceFromEntity(waste.get());
        return new ResponseEntity<>(wasteResource, HttpStatus.OK);
    }
}
