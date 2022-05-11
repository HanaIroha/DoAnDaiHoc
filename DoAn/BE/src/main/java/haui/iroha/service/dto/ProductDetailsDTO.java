package haui.iroha.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link haui.iroha.domain.ProductDetails} entity.
 */
public class ProductDetailsDTO implements Serializable {

    private Long idProductDetail;

    private Long idProduct;

    private String rom;

    private String color;

    private Long importQuantity;

    private Long quantity;

    private Long importPrice;

    private Long salePrice;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    public Long getIdProductDetail() {
        return idProductDetail;
    }

    public void setIdProductDetail(Long idProductDetail) {
        this.idProductDetail = idProductDetail;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getImportQuantity() {
        return importQuantity;
    }

    public void setImportQuantity(Long importQuantity) {
        this.importQuantity = importQuantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(Long importPrice) {
        this.importPrice = importPrice;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
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
        if (!(o instanceof ProductDetailsDTO)) {
            return false;
        }

        ProductDetailsDTO productDetailsDTO = (ProductDetailsDTO) o;
        if (this.idProductDetail == null) {
            return false;
        }
        return Objects.equals(this.idProductDetail, productDetailsDTO.idProductDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idProductDetail);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDetailsDTO{" +
            "idProductDetail=" + getIdProductDetail() +
            ", idProduct=" + getIdProduct() +
            ", rom='" + getRom() + "'" +
            ", color='" + getColor() + "'" +
            ", importQuantity=" + getImportQuantity() +
            ", quantity=" + getQuantity() +
            ", importPrice=" + getImportPrice() +
            ", salePrice=" + getSalePrice() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
