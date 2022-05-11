package haui.iroha.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link haui.iroha.domain.Comments} entity.
 */
public class CommentsDTO implements Serializable {

    private Long idComment;

    private String login;

    private Long idProduct;

    private Long idParentComment;

    private String content;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    public Long getIdComment() {
        return idComment;
    }

    public void setIdComment(Long idComment) {
        this.idComment = idComment;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getIdParentComment() {
        return idParentComment;
    }

    public void setIdParentComment(Long idParentComment) {
        this.idParentComment = idParentComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        if (!(o instanceof CommentsDTO)) {
            return false;
        }

        CommentsDTO commentsDTO = (CommentsDTO) o;
        if (this.idComment == null) {
            return false;
        }
        return Objects.equals(this.idComment, commentsDTO.idComment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idComment);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentsDTO{" +
            "idComment=" + getIdComment() +
            ", login='" + getLogin() + "'" +
            ", idProduct=" + getIdProduct() +
            ", idParentComment=" + getIdParentComment() +
            ", content='" + getContent() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
