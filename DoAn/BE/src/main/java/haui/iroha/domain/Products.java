package haui.iroha.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A Products.
 */
@Entity
@Table(name = "products")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;

    @Column(name = "id_category")
    private Long idCategory;

    @Column(name = "id_producer")
    private Long idProducer;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "code")
    private String code;

    @Column(name = "price")
    private Long price;

    @Column(name = "sale_percent")
    private Long salePercent;

    @Column(name = "last_price")
    private Long lastPrice;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "information")
    private String information;

    @Column(name = "information_details")
    private String informationDetails;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "is_disable")
    private Boolean isDisable;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getIdProduct() {
        return this.idProduct;
    }

    public Products idProduct(Long idProduct) {
        this.setIdProduct(idProduct);
        return this;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getIdCategory() {
        return this.idCategory;
    }

    public Products idCategory(Long idCategory) {
        this.setIdCategory(idCategory);
        return this;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Long getIdProducer() {
        return this.idProducer;
    }

    public Products idProducer(Long idProducer) {
        this.setIdProducer(idProducer);
        return this;
    }

    public void setIdProducer(Long idProducer) {
        this.idProducer = idProducer;
    }

    public String getName() {
        return this.name;
    }

    public Products name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return this.image;
    }

    public Products image(String image) {
        this.setImage(image);
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return this.code;
    }

    public Products code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getPrice() {
        return this.price;
    }

    public Products price(Long price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSalePercent() {
        return this.salePercent;
    }

    public Products salePercent(Long salePercent) {
        this.setSalePercent(salePercent);
        return this;
    }

    public void setSalePercent(Long salePercent) {
        this.salePercent = salePercent;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public Products quantity(Long quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public Long getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Long lastPrice) {
        this.lastPrice = lastPrice;
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
        return this.informationDetails;
    }

    public Products informationDetails(String informationDetails) {
        this.setInformationDetails(informationDetails);
        return this;
    }

    public void setInformationDetails(String informationDetails) {
        this.informationDetails = informationDetails;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Products createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public Products updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsDisable() {
        return this.isDisable;
    }

    public Products isDisable(Boolean isDisable) {
        this.setIsDisable(isDisable);
        return this;
    }

    public void setIsDisable(Boolean isDisable) {
        this.isDisable = isDisable;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Products)) {
            return false;
        }
        return idProduct != null && idProduct.equals(((Products) o).idProduct);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Products{" +
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
