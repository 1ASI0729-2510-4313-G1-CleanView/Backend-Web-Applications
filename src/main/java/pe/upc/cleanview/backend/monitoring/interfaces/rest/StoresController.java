package pe.upc.cleanview.backend.monitoring.interfaces.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pe.upc.cleanview.backend.iam.interfaces.acl.IamContextFacade;
import pe.upc.cleanview.backend.monitoring.domain.model.commands.DeleteStoreCommand;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetAllStoresQuery;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetStoreByIdQuery;
import pe.upc.cleanview.backend.monitoring.domain.model.queries.GetStoreByNameQuery;
import pe.upc.cleanview.backend.monitoring.domain.services.StoreCommandService;
import pe.upc.cleanview.backend.monitoring.domain.services.StoreQueryService;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.CreateStoreResource;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.StoreResource;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.resources.UpdateStoreResource;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.transform.CreateStoreCommandFromResourceAssembler;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.transform.StoreResourceFromEntityAssembler;
import pe.upc.cleanview.backend.monitoring.interfaces.rest.transform.UpdateStoreCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/monitoring/stores", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Stores", description = "Available Stores Endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class StoresController {

    private final StoreCommandService storeCommandService;
    private final StoreQueryService storeQueryService;
    private final IamContextFacade iamContextFacade;


    public StoresController(StoreCommandService storeCommandService,
                            StoreQueryService storeQueryService,
                            IamContextFacade iamContextFacade) {
        this.storeCommandService = storeCommandService;
        this.storeQueryService = storeQueryService;
        this.iamContextFacade = iamContextFacade;
    }


    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            throw new IllegalStateException("User is not authenticated or principal is not a UserDetails object.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return iamContextFacade.fetchUserIdByUsername(userDetails.getUsername());
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
