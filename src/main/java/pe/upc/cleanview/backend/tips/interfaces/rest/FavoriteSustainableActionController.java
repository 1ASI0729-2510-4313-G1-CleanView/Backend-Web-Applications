package pe.upc.cleanview.backend.tips.interfaces.rest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pe.upc.cleanview.backend.tips.domain.model.commands.RemoveFavoriteCommand;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllFavoriteActionsByUserIdQuery;
import pe.upc.cleanview.backend.tips.domain.model.queries.IsSustainableActionFavoriteForUserQuery;
import pe.upc.cleanview.backend.tips.domain.services.FavoriteCommandService;
import pe.upc.cleanview.backend.tips.domain.services.FavoriteQueryService;
import pe.upc.cleanview.backend.tips.interfaces.rest.resources.AddFavoriteResource;
import pe.upc.cleanview.backend.tips.interfaces.rest.resources.SustainableActionResource;
import pe.upc.cleanview.backend.tips.interfaces.rest.transform.AddFavoriteCommandFromResourceAssembler;
import pe.upc.cleanview.backend.tips.interfaces.rest.transform.SustainableActionResourceFromEntityAssembler;
import pe.upc.cleanview.backend.iam.interfaces.acl.IamContextFacade; // Usar el ACL

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing favorite sustainable actions for users.
 */
@RestController
@RequestMapping(value = "/api/v1/users/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Favorites", description = "Endpoints for managing user's favorite sustainable actions")
@SecurityRequirement(name = "Bearer Authentication")
public class FavoriteSustainableActionController {

    private final FavoriteCommandService favoriteCommandService;
    private final FavoriteQueryService favoriteQueryService;
    private final IamContextFacade iamContextFacade;

    public FavoriteSustainableActionController(FavoriteCommandService favoriteCommandService,
                                               FavoriteQueryService favoriteQueryService,
                                               IamContextFacade iamContextFacade) {
        this.favoriteCommandService = favoriteCommandService;
        this.favoriteQueryService = favoriteQueryService;
        this.iamContextFacade = iamContextFacade;
    }

    // Método auxiliar para obtener el userId del contexto de seguridad
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            throw new IllegalStateException("User is not authenticated or principal is not a UserDetails object.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return iamContextFacade.fetchUserIdByUsername(userDetails.getUsername());
    }

    /**
     * Adds a sustainable action to the current user's favorites.
     * Requires authentication.
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Add a sustainable action to favorites")// Solo usuarios autenticados@PreAuthorize("hasRole('PERSON') or hasRole('COMPANY')")    @Operation(summary = "Add a sustainable action to favorites")
    public ResponseEntity<SustainableActionResource> addFavorite(@RequestBody AddFavoriteResource resource) {
        Long userId = getCurrentUserId();
        var command = AddFavoriteCommandFromResourceAssembler.toCommandFromResource(userId, resource);
        var favorite = favoriteCommandService.handle(command);
        if (favorite.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        // Devuelve la acción sostenible que se acaba de añadir a favoritos
        var sustainableActionResource = SustainableActionResourceFromEntityAssembler.toResourceFromEntity(favorite.get().getSustainableAction());
        return new ResponseEntity<>(sustainableActionResource, HttpStatus.CREATED);
    }

    /**
     * Removes a sustainable action from the current user's favorites.
     * Requires authentication.
     */
    @DeleteMapping("/{sustainableActionId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Remove a sustainable action from favorites")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long sustainableActionId) {
        Long userId = getCurrentUserId();
        favoriteCommandService.handle(new RemoveFavoriteCommand(userId, sustainableActionId));
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all favorite sustainable actions for the current user.
     * Requires authentication.
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all favorite sustainable actions for the current user")
    public ResponseEntity<List<SustainableActionResource>> getAllUserFavorites() {
        Long userId = getCurrentUserId();
        var actions = favoriteQueryService.handle(new GetAllFavoriteActionsByUserIdQuery(userId));
        if (actions.isEmpty()) {
            return ResponseEntity.ok(List.of()); // Devuelve una lista vacía si no hay favoritos
        }
        var resources = actions.stream()
                .map(SustainableActionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    /**
     * Checks if a specific sustainable action is favorite for the current user.
     * Requires authentication.
     */
    @GetMapping("/check/{sustainableActionId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Check if a sustainable action is favorite for the current user")
    public ResponseEntity<Boolean> isFavorite(@PathVariable Long sustainableActionId) {
        Long userId = getCurrentUserId();
        boolean isFavorite = favoriteQueryService.handle(new IsSustainableActionFavoriteForUserQuery(userId, sustainableActionId));
        return ResponseEntity.ok(isFavorite);
    }
}