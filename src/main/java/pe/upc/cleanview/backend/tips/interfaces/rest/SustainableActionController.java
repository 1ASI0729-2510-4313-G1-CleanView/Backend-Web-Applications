package pe.upc.cleanview.backend.tips.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.upc.cleanview.backend.tips.domain.model.commands.DeleteSustainableActionCommand;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllSustainableActionTypesQuery;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetSustainableActionsByTypeQuery;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllSustainableActionsQuery;
import pe.upc.cleanview.backend.tips.domain.model.valueobjects.SustainableActionType;
import pe.upc.cleanview.backend.tips.domain.services.SustainableActionCommandService;
import pe.upc.cleanview.backend.tips.domain.services.SustainableActionQueryService;
import pe.upc.cleanview.backend.tips.interfaces.rest.resources.SustainableActionResource;
import pe.upc.cleanview.backend.tips.interfaces.rest.resources.CreateSustainableActionResource;
import pe.upc.cleanview.backend.tips.interfaces.rest.transform.SustainableActionResourceFromEntityAssembler;
import pe.upc.cleanview.backend.tips.interfaces.rest.transform.CreateSustainableActionCommandFromResourceAssembler;

import java.util.Arrays;
import java.util.List;

/**
 * REST controller for managing Sustainable Actions.
 * Provides endpoints for creating, retrieving, and deleting sustainable actions.
 */
@RestController
@RequestMapping(value = "/api/v1/sustainable-actions", produces = MediaType.APPLICATION_JSON_VALUE) // Changed request mapping path
@Tag(name = "Sustainable Actions", description = "Available Sustainable Actions Endpoints")
public class SustainableActionController {
    private final SustainableActionCommandService sustainableActionCommandService;
    private final SustainableActionQueryService sustainableActionQueryService;

    /**
     * Constructs a new SustainableActionController.
     *
     * @param sustainableActionCommandService The service for handling sustainable action commands.
     * @param sustainableActionQueryService   The service for handling sustainable action queries.
     */
    SustainableActionController(SustainableActionCommandService sustainableActionCommandService, SustainableActionQueryService sustainableActionQueryService) {
        this.sustainableActionCommandService = sustainableActionCommandService;
        this.sustainableActionQueryService = sustainableActionQueryService;
    }

    /**
     * Creates a new sustainable action.
     *
     * @param resource The resource containing the data for the new sustainable action.
     * @return A {@link ResponseEntity} containing the created {@link SustainableActionResource} if successful,
     * or a bad request response if the action could not be created.
     */
    @PostMapping
    @Operation(summary = "Create a new sustainable action", description = "Creates a new sustainable action with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sustainable action created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or unable to create the sustainable action")
    })
    public ResponseEntity<SustainableActionResource> createAction(@RequestBody CreateSustainableActionResource resource){
        var command = CreateSustainableActionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = sustainableActionCommandService.handle(command);

        if (result.isEmpty()) return ResponseEntity.badRequest().build();
        var created = result.get();
        var response = SustainableActionResourceFromEntityAssembler.toResourceFromEntity(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves all sustainable actions.
     *
     * @return A {@link ResponseEntity} containing a list of {@link SustainableActionResource} if actions are found,
     * or a not found response if no actions are available.
     */
    @GetMapping
    @Operation(summary = "Get all sustainable actions", description = "Retrieves a list of all available sustainable actions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of sustainable actions"),
            @ApiResponse(responseCode = "404", description = "No sustainable actions found")
    })
    public ResponseEntity<List<SustainableActionResource>> getAllActions() {
        var actions = sustainableActionQueryService.handle(new GetAllSustainableActionsQuery());

        if (actions.isEmpty()) return ResponseEntity.notFound().build();

        var resources = actions.stream()
                .map(SustainableActionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    /**
     * Retrieves sustainable actions filtered by type.
     *
     * @param type The type of sustainable action to filter by (e.g., "STORAGE", "OPERATIONAL", "REGULATION").
     * @return A {@link ResponseEntity} containing a list of {@link SustainableActionResource} matching the specified type.
     * The list will be empty if no actions match the type.
     */
    @GetMapping("/type/{type}")
    @Operation(summary = "Get sustainable actions by type", description = "Retrieves a list of sustainable actions filtered by their type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved sustainable actions by type (list may be empty if no matches)"),
            @ApiResponse(responseCode = "400", description = "Invalid type provided in the path variable") // Assuming conversion might fail
    })
    public ResponseEntity<List<SustainableActionResource>> getActionsByType(@PathVariable String type) {
        // Note: The service handles the conversion from String 'type' to SustainableActionType enum.
        // If the `fromString` method in SustainableActionType throws IllegalStateException for invalid types,
        // this would result in a 500 error by default. A more robust solution might include custom exception handling
        // to return a 400 Bad Request for invalid type strings.
        var actions = sustainableActionQueryService.handle(new GetSustainableActionsByTypeQuery(type));
        var resources = actions.stream()
                .map(SustainableActionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Deletes a sustainable action by its ID.
     *
     * @param id The ID of the sustainable action to delete.
     * @return A {@link ResponseEntity} indicating the success of the deletion.
     * Returns 200 OK if the command was processed (note: this endpoint currently does not confirm
     * if the action existed before deletion, it only indicates that the command was sent).
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a sustainable action by ID", description = "Deletes a sustainable action based on its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sustainable action deletion command processed successfully"),
            // Potentially add 404 if the command service could indicate resource not found
    })
    public ResponseEntity<?> deleteAction(@PathVariable Long id){
        sustainableActionCommandService.handle(new DeleteSustainableActionCommand(id));
        return ResponseEntity.ok().build(); // Consider ResponseEntity.noContent().build() for 204 No Content for deletions
    }

    @GetMapping("/types")
    @Operation(
            summary = "Get all sustainable action types",
            description = "Returns a list of all sustainable action types."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of sustainable action types")
            }
    )
    public ResponseEntity<List<String>> getAllTypes() {
        var types = sustainableActionQueryService.handle(new GetAllSustainableActionTypesQuery());
        return ResponseEntity.ok(types);
    }
}
