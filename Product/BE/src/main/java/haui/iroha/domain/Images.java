package haui.iroha.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Images.
 */
@Entity
@Table(name = "images")
public class Images implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image")
    private Long idImage;

    @Column(name = "id_product")
    private Long idProduct;

    @Column(name = "image_url")
    private String imageUrl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getIdImage() {
        return this.idImage;
    }

    public Images idImage(Long idImage) {
        this.setIdImage(idImage);
        return this;
    }

    public void setIdImage(Long idImage) {
        this.idImage = idImage;
    }

    public Long getIdProduct() {
        return this.idProduct;
    }

    public Images idProduct(Long idProduct) {
        this.setIdProduct(idProduct);
        return this;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Images imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Images)) {
            return false;
        }
        return idImage != null && idImage.equals(((Images) o).idImage);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Images{" +
            "idImage=" + getIdImage() +
            ", idProduct=" + getIdProduct() +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
