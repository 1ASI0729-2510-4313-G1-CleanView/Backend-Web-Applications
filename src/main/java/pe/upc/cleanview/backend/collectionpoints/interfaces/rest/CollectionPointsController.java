package pe.upc.cleanview.backend.collectionpoints.interfaces.rest;



import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.upc.cleanview.backend.collectionpoints.domain.model.commands.DeleteCollectionPointCommand;
import pe.upc.cleanview.backend.collectionpoints.domain.model.queries.GetAllCollectionPointsQuery;
import pe.upc.cleanview.backend.collectionpoints.domain.model.queries.GetCollectionPointByIdQuery;
import pe.upc.cleanview.backend.collectionpoints.domain.model.queries.GetCollectionPointByNameQuery;
import pe.upc.cleanview.backend.collectionpoints.domain.services.CollectionPointCommandService;
import pe.upc.cleanview.backend.collectionpoints.domain.services.CollectionPointQueryService;
import pe.upc.cleanview.backend.collectionpoints.interfaces.rest.resources.CreateCollectionPointResource;
import pe.upc.cleanview.backend.collectionpoints.interfaces.rest.resources.UpdateCollectionPointResource;
import pe.upc.cleanview.backend.collectionpoints.interfaces.rest.resources.CollectionPointResource;
import pe.upc.cleanview.backend.collectionpoints.interfaces.rest.transform.*;
import pe.upc.cleanview.backend.collectionpoints.interfaces.rest.transform.CollectionPointResourceFromEntityAssembler;
import pe.upc.cleanview.backend.collectionpoints.interfaces.rest.transform.CreateCollectionPointCommandFromResourceAssembler;
import pe.upc.cleanview.backend.collectionpoints.interfaces.rest.transform.UpdateCollectionPointCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/collection-points", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Collection Points", description = "Collection Point Management Endpoints")
public class CollectionPointsController {

    private final CollectionPointQueryService collectionPointQueryService;
    private final CollectionPointCommandService collectionPointCommandService;

    public CollectionPointsController(CollectionPointQueryService collectionPointQueryService,
                                      CollectionPointCommandService collectionPointCommandService) {
        this.collectionPointQueryService = collectionPointQueryService;
        this.collectionPointCommandService = collectionPointCommandService;
    }

    @PostMapping
    public ResponseEntity<CollectionPointResource> create(@RequestBody CreateCollectionPointResource resource) {
        var command = CreateCollectionPointCommandFromResourceAssembler.toCommandFromResource(resource);
        var id = collectionPointCommandService.handle(command);
        if (id.equals(0L)) return ResponseEntity.badRequest().build();

        var result = collectionPointQueryService.handle(new GetCollectionPointByIdQuery(id));
        return result.map(collectionPoint -> new ResponseEntity<>(
                        CollectionPointResourceFromEntityAssembler.toResourceFromEntity(collectionPoint),
                        HttpStatus.CREATED))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<List<CollectionPointResource>> getAll() {
        var result = collectionPointQueryService.handle(new GetAllCollectionPointsQuery());
        var resources = result.stream()
                .map(CollectionPointResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionPointResource> getById(@PathVariable Long id) {
        var result = collectionPointQueryService.handle(new GetCollectionPointByIdQuery(id));
        return result.map(collectionPoint ->
                        ResponseEntity.ok(CollectionPointResourceFromEntityAssembler.toResourceFromEntity(collectionPoint)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<CollectionPointResource> getByName(@RequestParam String name) {
        var result = collectionPointQueryService.handle(new GetCollectionPointByNameQuery(name));
        return result.map(collectionPoint ->
                        ResponseEntity.ok(CollectionPointResourceFromEntityAssembler.toResourceFromEntity(collectionPoint)))
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<CollectionPointResource> update(@PathVariable Long id,
                                                          @RequestBody UpdateCollectionPointResource resource) {
        var command = UpdateCollectionPointCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = collectionPointCommandService.handle(command);
        return result.map(updated ->
                        ResponseEntity.ok(CollectionPointResourceFromEntityAssembler.toResourceFromEntity(updated)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        collectionPointCommandService.handle(new DeleteCollectionPointCommand(id));
        return ResponseEntity.noContent().build();
    }
}
