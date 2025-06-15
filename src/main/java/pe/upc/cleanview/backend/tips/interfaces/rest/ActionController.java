package pe.upc.cleanview.backend.tips.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.upc.cleanview.backend.tips.domain.model.aggregates.Action;
import pe.upc.cleanview.backend.tips.domain.model.queries.GetAllActionsQuery;
import pe.upc.cleanview.backend.tips.domain.services.ActionCommandService;
import pe.upc.cleanview.backend.tips.domain.services.ActionQueryService;
import pe.upc.cleanview.backend.tips.interfaces.rest.resources.ActionResource;
import pe.upc.cleanview.backend.tips.interfaces.rest.resources.CreateActionResource;
import pe.upc.cleanview.backend.tips.interfaces.rest.transform.ActionResourceFromEntityAssembler;
import pe.upc.cleanview.backend.tips.interfaces.rest.transform.CreateActionCommandFromResourceAssembler;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actions")
@Tag(name = "Actions", description = "Available Actions Endpoints")
public class ActionController {
    private final ActionCommandService actionCommandService;
    private final ActionQueryService actionQueryService;

    ActionController(ActionCommandService actionCommandService, ActionQueryService actionQueryService) {
        this.actionCommandService = actionCommandService;
        this.actionQueryService = actionQueryService;
    }

    @PostMapping
    public ResponseEntity<ActionResource> createAction(@RequestBody CreateActionResource resource){
        var command = CreateActionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = actionCommandService.handle(command);

        if (result.isEmpty()) return ResponseEntity.badRequest().build();
        var created = result.get();
        var response = ActionResourceFromEntityAssembler.toResourceFromEntity(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ActionResource>> getAllActions() {
        var actions = actionQueryService.handle(new GetAllActionsQuery());

        if (actions.isEmpty()) return ResponseEntity.notFound().build();

        var resources = actions.stream()
                .map(ActionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }


}
