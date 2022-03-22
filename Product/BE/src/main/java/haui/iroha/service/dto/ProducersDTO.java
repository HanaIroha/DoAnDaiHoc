package haui.iroha.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link haui.iroha.domain.Producers} entity.
 */
public class ProducersDTO implements Serializable {

    private Long idProducer;

    private String name;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

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
        if (!(o instanceof ProducersDTO)) {
            return false;
        }

        ProducersDTO producersDTO = (ProducersDTO) o;
        if (this.idProducer == null) {
            return false;
        }
        return Objects.equals(this.idProducer, producersDTO.idProducer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idProducer);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProducersDTO{" +
            "idProducer=" + getIdProducer() +
            ", name='" + getName() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
