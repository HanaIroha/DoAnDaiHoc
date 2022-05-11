package haui.iroha.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link haui.iroha.domain.ProductSpecs} entity.
 */
public class ProductSpecsDTO implements Serializable {

    private Long idProductSpec;

    private Long idProduct;

    private String key;

    private String value;

    public Long getIdProductSpec() {
        return idProductSpec;
    }

    public void setIdProductSpec(Long idProductSpec) {
        this.idProductSpec = idProductSpec;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductSpecsDTO)) {
            return false;
        }

        ProductSpecsDTO productSpecsDTO = (ProductSpecsDTO) o;
        if (this.idProductSpec == null) {
            return false;
        }
        return Objects.equals(this.idProductSpec, productSpecsDTO.idProductSpec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idProductSpec);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductSpecsDTO{" +
            "idProductSpec=" + getIdProductSpec() +
            ", idProduct=" + getIdProduct() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
