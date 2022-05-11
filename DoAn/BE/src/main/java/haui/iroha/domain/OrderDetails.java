package haui.iroha.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A OrderDetails.
 */
@Entity
@Table(name = "order_details")
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_detail")
    private Long idOrderDetail;

    @Column(name = "id_order")
    private Long idOrder;

    @Column(name = "id_product")
    private Long idProduct;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "price")
    private Long price;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getIdOrderDetail() {
        return this.idOrderDetail;
    }

    public OrderDetails idOrderDetail(Long idOrderDetail) {
        this.setIdOrderDetail(idOrderDetail);
        return this;
    }

    public void setIdOrderDetail(Long idOrderDetail) {
        this.idOrderDetail = idOrderDetail;
    }

    public Long getIdOrder() {
        return this.idOrder;
    }

    public OrderDetails idOrder(Long idOrder) {
        this.setIdOrder(idOrder);
        return this;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Long getIdProduct() {
        return this.idProduct;
    }

    public OrderDetails idProduct(Long idProduct) {
        this.setIdProduct(idProduct);
        return this;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public OrderDetails quantity(Long quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return this.price;
    }

    public OrderDetails price(Long price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public OrderDetails createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public OrderDetails updatedAt(ZonedDateTime updatedAt) {
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
        if (!(o instanceof OrderDetails)) {
            return false;
        }
        return idOrderDetail != null && idOrderDetail.equals(((OrderDetails) o).idOrderDetail);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDetails{" +
            "idOrderDetail=" + getIdOrderDetail() +
            ", idOrder=" + getIdOrder() +
            ", idProduct=" + getIdProduct() +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
