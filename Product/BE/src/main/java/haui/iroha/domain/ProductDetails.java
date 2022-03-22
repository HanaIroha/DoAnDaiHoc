package haui.iroha.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A ProductDetails.
 */
@Entity
@Table(name = "product_details")
public class ProductDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_detail")
    private Long idProductDetail;

    @Column(name = "id_product")
    private Long idProduct;

    @Column(name = "rom")
    private String rom;

    @Column(name = "color")
    private String color;

    @Column(name = "import_quantity")
    private Long importQuantity;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "import_price")
    private Long importPrice;

    @Column(name = "sale_price")
    private Long salePrice;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getIdProductDetail() {
        return this.idProductDetail;
    }

    public ProductDetails idProductDetail(Long idProductDetail) {
        this.setIdProductDetail(idProductDetail);
        return this;
    }

    public void setIdProductDetail(Long idProductDetail) {
        this.idProductDetail = idProductDetail;
    }

    public Long getIdProduct() {
        return this.idProduct;
    }

    public ProductDetails idProduct(Long idProduct) {
        this.setIdProduct(idProduct);
        return this;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getRom() {
        return this.rom;
    }

    public ProductDetails rom(String rom) {
        this.setRom(rom);
        return this;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getColor() {
        return this.color;
    }

    public ProductDetails color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getImportQuantity() {
        return this.importQuantity;
    }

    public ProductDetails importQuantity(Long importQuantity) {
        this.setImportQuantity(importQuantity);
        return this;
    }

    public void setImportQuantity(Long importQuantity) {
        this.importQuantity = importQuantity;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public ProductDetails quantity(Long quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getImportPrice() {
        return this.importPrice;
    }

    public ProductDetails importPrice(Long importPrice) {
        this.setImportPrice(importPrice);
        return this;
    }

    public void setImportPrice(Long importPrice) {
        this.importPrice = importPrice;
    }

    public Long getSalePrice() {
        return this.salePrice;
    }

    public ProductDetails salePrice(Long salePrice) {
        this.setSalePrice(salePrice);
        return this;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public ProductDetails createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public ProductDetails updatedAt(ZonedDateTime updatedAt) {
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
        if (!(o instanceof ProductDetails)) {
            return false;
        }
        return idProductDetail != null && idProductDetail.equals(((ProductDetails) o).idProductDetail);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDetails{" +
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
