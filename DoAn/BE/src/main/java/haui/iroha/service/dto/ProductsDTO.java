package haui.iroha.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link haui.iroha.domain.Products} entity.
 */
public class ProductsDTO implements Serializable {

    private Long idProduct;

    private Long idCategory;

    private Long idProducer;

    private String name;

    private String image;

    private String code;

    private Long price;

    private Long salePercent;

    private Long lastPrice;

    private Long quantity;

    private String information;

    private String informationDetails;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private Boolean isDisable;

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Long getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(Long idProducer) {
        this.idProducer = idProducer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSalePercent() {
        return salePercent;
    }

    public void setSalePercent(Long salePercent) {
        this.salePercent = salePercent;
    }

    public Long getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Long lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getInformationDetails() {
        return informationDetails;
    }

    public void setInformationDetails(String informationDetails) {
        this.informationDetails = informationDetails;
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

    public Boolean getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Boolean isDisable) {
        this.isDisable = isDisable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductsDTO)) {
            return false;
        }

        ProductsDTO productsDTO = (ProductsDTO) o;
        if (this.idProduct == null) {
            return false;
        }
        return Objects.equals(this.idProduct, productsDTO.idProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idProduct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductsDTO{" +
            "idProduct=" + getIdProduct() +
            ", idCategory=" + getIdCategory() +
            ", idProducer=" + getIdProducer() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", code='" + getCode() + "'" +
            ", price=" + getPrice() +
            ", salePercent=" + getSalePercent() +
            ", lastPrice=" + getLastPrice() +
            ", quantity=" + getQuantity() +
            ", information='" + getInformation() + "'" +
            ", informationDetails='" + getInformationDetails() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", isDisable='" + getIsDisable() + "'" +
            "}";
    }
}
