package haui.iroha.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A ProductSpecs.
 */
@Entity
@Table(name = "product_specs")
public class ProductSpecs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product_spec")
    private Long idProductSpec;

    @Column(name = "id_product")
    private Long idProduct;

    @Column(name = "keyname")
    private String key;

    @Column(name = "value")
    private String value;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getIdProductSpec() {
        return this.idProductSpec;
    }

    public ProductSpecs idProductSpec(Long idProductSpec) {
        this.setIdProductSpec(idProductSpec);
        return this;
    }

    public void setIdProductSpec(Long idProductSpec) {
        this.idProductSpec = idProductSpec;
    }

    public Long getIdProduct() {
        return this.idProduct;
    }

    public ProductSpecs idProduct(Long idProduct) {
        this.setIdProduct(idProduct);
        return this;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getKey() {
        return this.key;
    }

    public ProductSpecs key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public ProductSpecs value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSpecs)) {
            return false;
        }
        return idProductSpec != null && idProductSpec.equals(((ProductSpecs) o).idProductSpec);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSpecs{" +
            "idProductSpec=" + getIdProductSpec() +
            ", idProduct=" + getIdProduct() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
