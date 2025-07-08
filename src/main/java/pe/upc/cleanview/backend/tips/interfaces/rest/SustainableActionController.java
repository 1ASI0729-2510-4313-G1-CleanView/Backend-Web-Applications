package pe.upc.cleanview.backend.tips.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement; // Importar para seguridad
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Importar para seguridad
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pe.upc.cleanview.backend.iam.interfaces.acl.IamContextFacade; // Importar IamContextFacade
import pe.upc.cleanview.backend.tips.domain.model.commands.DeleteSustainableActionCommand;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllSustainableActionTypesQuery;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetSustainableActionsByTypeQuery;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllSustainableActionsQuery;
import pe.upc.cleanview.backend.tips.domain.services.SustainableActionCommandService;
import pe.upc.cleanview.backend.tips.domain.services.SustainableActionQueryService;
import pe.upc.cleanview.backend.tips.interfaces.rest.resources.CreateSustainableActionResource;
import pe.upc.cleanview.backend.tips.interfaces.rest.resources.SustainableActionResource;
import pe.upc.cleanview.backend.tips.interfaces.rest.transform.CreateSustainableActionCommandFromResourceAssembler;
import pe.upc.cleanview.backend.tips.interfaces.rest.transform.SustainableActionResourceFromEntityAssembler;

import java.util.List;

/**
 * Controller for Sustainable Actions.
 * Provides endpoints for creating, retrieving, and deleting sustainable actions.
 */
@RestController
@RequestMapping(value = "/api/v1/sustainable-actions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Sustainable Actions", description = "Available Sustainable Actions Endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class SustainableActionController {

    private final SustainableActionCommandService sustainableActionCommandService;
    private final SustainableActionQueryService sustainableActionQueryService;
    private final IamContextFacade iamContextFacade;

    /**
     * Constructs a new SustainableActionController.
     * @param sustainableActionCommandService Service to handle commands.
     * @param sustainableActionQueryService Service to handle queries.
     */
    SustainableActionController(SustainableActionCommandService sustainableActionCommandService,
                                SustainableActionQueryService sustainableActionQueryService,
                                IamContextFacade iamContextFacade) {
        this.sustainableActionCommandService = sustainableActionCommandService;
        this.sustainableActionQueryService = sustainableActionQueryService;
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

    /**
     * Creates a new sustainable action.
     * @param resource The {@link CreateSustainableActionResource} containing the new action's data.
     * @return The created {@link SustainableActionResource}, or HTTP 400 if creation failed.
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new sustainable action")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sustainable action created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payload or unable to create the action")
    })
    public ResponseEntity<SustainableActionResource> createAction(@RequestBody CreateSustainableActionResource resource) {
        Long creatorUserId = getCurrentUserId();

        var command = CreateSustainableActionCommandFromResourceAssembler.toCommandFromResource(resource, creatorUserId);
        var result = sustainableActionCommandService.handle(command);

        if (result.isEmpty()) return ResponseEntity.badRequest().build();

        var created = result.get();
        var response = SustainableActionResourceFromEntityAssembler.toResourceFromEntity(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves all sustainable actions.
     * @return A list of {@link SustainableActionResource}, or HTTP 404 if none found.
     */
    @GetMapping
    @Operation(summary = "Get all sustainable actions")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sustainable actions found"),
            @ApiResponse(responseCode = "404", description = "No sustainable actions available")
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
     * Retrieves sustainable actions by their type.
     * @param type Sustainable action type as string (e.g. STORAGE, OPERATIONAL, REGULATION).
     * @return A list of {@link SustainableActionResource} filtered by the given type.
     */
    @GetMapping("/type/{type}")
    @Operation(summary = "Get sustainable actions by type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sustainable actions successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid type specified")
    })
    public ResponseEntity<List<SustainableActionResource>> getActionsByType(@PathVariable String type) {
        var actions = sustainableActionQueryService.handle(new GetSustainableActionsByTypeQuery(type));
        var resources = actions.stream()
                .map(SustainableActionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Deletes a sustainable action by its ID.
     * @param id The unique identifier of the action to delete.
     * @return HTTP 200 if deletion was successfully processed.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete a sustainable action by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sustainable action deletion processed successfully"),
            @ApiResponse(responseCode = "403", description = "User not authorized to delete this action") // Nuevo código de respuesta
    })
    public ResponseEntity<Void> deleteAction(@PathVariable Long id) {
        sustainableActionCommandService.handle(new DeleteSustainableActionCommand(id));
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves all sustainable action types.
     * @return A list of all available action types as Strings.
     */
    @GetMapping("/types")
    @Operation(summary = "Get all sustainable action types")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Types successfully retrieved")
    })
    public ResponseEntity<List<String>> getAllTypes() {
        var types = sustainableActionQueryService.handle(new GetAllSustainableActionTypesQuery());
        return ResponseEntity.ok(types);
    }
}
