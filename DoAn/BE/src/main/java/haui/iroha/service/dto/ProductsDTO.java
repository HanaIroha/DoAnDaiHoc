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

    private Long quantity;

    private String supportSim;

    private String monitor;

    private String color;

    private String frontCamera;

    private String rearCamera;

    private String cPU;

    private String gPU;

    private String rAM;

    private String rOM;

    private String oS;

    private String pin;

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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getSupportSim() {
        return supportSim;
    }

    public void setSupportSim(String supportSim) {
        this.supportSim = supportSim;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFrontCamera() {
        return frontCamera;
    }

    public void setFrontCamera(String frontCamera) {
        this.frontCamera = frontCamera;
    }

    public String getRearCamera() {
        return rearCamera;
    }

    public void setRearCamera(String rearCamera) {
        this.rearCamera = rearCamera;
    }

    public String getcPU() {
        return cPU;
    }

    public void setcPU(String cPU) {
        this.cPU = cPU;
    }

    public String getgPU() {
        return gPU;
    }

    public void setgPU(String gPU) {
        this.gPU = gPU;
    }

    public String getrAM() {
        return rAM;
    }

    public void setrAM(String rAM) {
        this.rAM = rAM;
    }

    public String getrOM() {
        return rOM;
    }

    public void setrOM(String rOM) {
        this.rOM = rOM;
    }

    public String getoS() {
        return oS;
    }

    public void setoS(String oS) {
        this.oS = oS;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
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
            ", quantity=" + getQuantity() +
            ", supportSim='" + getSupportSim() + "'" +
            ", monitor='" + getMonitor() + "'" +
            ", color='" + getColor() + "'" +
            ", frontCamera='" + getFrontCamera() + "'" +
            ", rearCamera='" + getRearCamera() + "'" +
            ", cPU='" + getcPU() + "'" +
            ", gPU='" + getgPU() + "'" +
            ", rAM='" + getrAM() + "'" +
            ", rOM='" + getrOM() + "'" +
            ", oS='" + getoS() + "'" +
            ", pin='" + getPin() + "'" +
            ", informationDetails='" + getInformationDetails() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", isDisable='" + getIsDisable() + "'" +
            "}";
    }
}
