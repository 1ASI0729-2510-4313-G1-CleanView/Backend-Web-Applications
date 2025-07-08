package pe.upc.cleanview.backend.tips.domain.model.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class FavoriteId implements Serializable {

    private Long userId;
    private Long sustainableActionId;

    public FavoriteId() {}

    public FavoriteId(Long userId, Long sustainableActionId) {
        this.userId = userId;
        this.sustainableActionId = sustainableActionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(sustainableActionId, that.sustainableActionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sustainableActionId);
    }
}