package haui.iroha.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link haui.iroha.domain.Categories} entity.
 */
public class CategoriesDTO implements Serializable {

    private Long idCategory;

    private String name;

    private Long parentId;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriesDTO)) {
            return false;
        }

        CategoriesDTO categoriesDTO = (CategoriesDTO) o;
        if (this.idCategory == null) {
            return false;
        }
        return Objects.equals(this.idCategory, categoriesDTO.idCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCategory);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriesDTO{" +
            "idCategory=" + getIdCategory() +
            ", name='" + getName() + "'" +
            ", parentId=" + getParentId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
