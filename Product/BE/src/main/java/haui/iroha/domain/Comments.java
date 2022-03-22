package haui.iroha.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A Comments.
 */
@Entity
@Table(name = "comments")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    private Long idComment;

    @Column(name = "login")
    private String login;

    @Column(name = "id_product")
    private Long idProduct;

    @Column(name = "id_parent_comment")
    private Long idParentComment;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getIdComment() {
        return this.idComment;
    }

    public Comments idComment(Long idComment) {
        this.setIdComment(idComment);
        return this;
    }

    public void setIdComment(Long idComment) {
        this.idComment = idComment;
    }

    public String getLogin() {
        return this.login;
    }

    public Comments login(String login) {
        this.setLogin(login);
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getIdProduct() {
        return this.idProduct;
    }

    public Comments idProduct(Long idProduct) {
        this.setIdProduct(idProduct);
        return this;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getIdParentComment() {
        return this.idParentComment;
    }

    public Comments idParentComment(Long idParentComment) {
        this.setIdParentComment(idParentComment);
        return this;
    }

    public void setIdParentComment(Long idParentComment) {
        this.idParentComment = idParentComment;
    }

    public String getContent() {
        return this.content;
    }

    public Comments content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Comments createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public Comments updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comments)) {
            return false;
        }
        return idComment != null && idComment.equals(((Comments) o).idComment);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comments{" +
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
