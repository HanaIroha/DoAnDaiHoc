package haui.iroha.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link haui.iroha.domain.Images} entity.
 */
public class ImagesDTO implements Serializable {

    private Long idImage;

    private Long idProduct;

    private String imageUrl;

    public Long getIdImage() {
        return idImage;
    }

    public void setIdImage(Long idImage) {
        this.idImage = idImage;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImagesDTO)) {
            return false;
        }

        ImagesDTO imagesDTO = (ImagesDTO) o;
        if (this.idImage == null) {
            return false;
        }
        return Objects.equals(this.idImage, imagesDTO.idImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idImage);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImagesDTO{" +
            "idImage=" + getIdImage() +
            ", idProduct=" + getIdProduct() +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
