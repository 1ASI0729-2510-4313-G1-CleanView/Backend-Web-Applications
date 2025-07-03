package com.acme.center.platform.monitoring.interfaces.rest;

import com.acme.center.platform.monitoring.domain.model.commands.DeleteStoreCommand;
import com.acme.center.platform.monitoring.domain.model.queries.GetAllStoresQuery;
import com.acme.center.platform.monitoring.domain.model.queries.GetStoreByIdQuery;
import com.acme.center.platform.monitoring.domain.model.queries.GetStoreByNameQuery;
import com.acme.center.platform.monitoring.domain.services.StoreCommandService;
import com.acme.center.platform.monitoring.domain.services.StoreQueryService;
import com.acme.center.platform.monitoring.interfaces.rest.resources.CreateStoreResource;
import com.acme.center.platform.monitoring.interfaces.rest.resources.StoreResource;
import com.acme.center.platform.monitoring.interfaces.rest.resources.UpdateStoreResource;
import com.acme.center.platform.monitoring.interfaces.rest.transform.CreateStoreCommandFromResourceAssembler;
import com.acme.center.platform.monitoring.interfaces.rest.transform.StoreResourceFromEntityAssembler;
import com.acme.center.platform.monitoring.interfaces.rest.transform.UpdateStoreCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/stores", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Stores", description = "Available Store Endpoints")
public class StoresController {

    private final StoreCommandService storeCommandService;
    private final StoreQueryService storeQueryService;


    public StoresController(StoreCommandService storeCommandService, StoreQueryService storeQueryService) {
        this.storeCommandService = storeCommandService;
        this.storeQueryService = storeQueryService;
    }

    @PostMapping
    @Operation(summary = "Create Store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Store created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<StoreResource> createStore(
            @RequestBody CreateStoreResource resource
            ){
        var createStoreCommand = CreateStoreCommandFromResourceAssembler.toCommandFromResource(resource);
        var store = storeCommandService.handle(createStoreCommand);
        if (store.isEmpty()) return ResponseEntity.badRequest().build();
        var createStore = store.get();
        var storeResource = StoreResourceFromEntityAssembler.toResourceFromEntity(createStore);
        return new ResponseEntity<>(storeResource, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update Store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store updated"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<StoreResource> updateStore(@PathVariable Long id, @RequestBody UpdateStoreResource resource) {
        var updateStoreCommand = UpdateStoreCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var store = storeCommandService.handle(updateStoreCommand);
        if (store.isEmpty()) return ResponseEntity.badRequest().build();
        var updateStore = store.get();
        var storeResource = StoreResourceFromEntityAssembler.toResourceFromEntity(updateStore);
        return new ResponseEntity<>(storeResource, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Store")
    public ResponseEntity<StoreResource> deleteStore(@PathVariable Long id) {
        var deleteStoreCommand = new DeleteStoreCommand(id);
        storeCommandService.handle(deleteStoreCommand);
        return ResponseEntity.ok().build();
    }




    //Queries


    @GetMapping
    @Operation(summary = "Get all stores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stores Found"),
            @ApiResponse(responseCode = "404", description = "Stores not Found")
    })
    public ResponseEntity<List<StoreResource>> getAllStores() {
        var stores = storeQueryService.handle(new GetAllStoresQuery());
        if (stores.isEmpty()) return ResponseEntity.badRequest().build();
        var storeResource = stores.stream()
                .map(StoreResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return new ResponseEntity<>(storeResource, HttpStatus.OK);
    }




    @GetMapping("/{id}")
    @Operation(summary = "Get store by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store Found"),
            @ApiResponse(responseCode = "404", description = "Store not Found")
    })
    public ResponseEntity<StoreResource> getStoreById(
            @PathVariable Long id
    ){
        var getStoreByIdQuery = new GetStoreByIdQuery(id);
        var store = storeQueryService.handle(getStoreByIdQuery);
        if (store.isEmpty()) return ResponseEntity.badRequest().build();
        var storeResource = StoreResourceFromEntityAssembler.toResourceFromEntity(store.get());
        return new ResponseEntity<>(storeResource, HttpStatus.OK);
    }


    @GetMapping("/{name}")
    @Operation(summary = "Get store by Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store Found"),
            @ApiResponse(responseCode = "404", description = "Store not Found")
    })
    public ResponseEntity<StoreResource> getStoreByName(
            @PathVariable String name
    ){
        var getStoreByNameQuery = new GetStoreByNameQuery(name);
        var store = storeQueryService.handle(getStoreByNameQuery);
        if (store.isEmpty()) return ResponseEntity.badRequest().build();
        var storeResource = StoreResourceFromEntityAssembler.toResourceFromEntity(store.get());
        return new ResponseEntity<>(storeResource, HttpStatus.OK);
    }

}
