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

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "support_sim")
    private String supportSim;

    @Column(name = "monitor")
    private String monitor;

    @Column(name = "color")
    private String color;

    @Column(name = "front_camera")
    private String frontCamera;

    @Column(name = "rear_camera")
    private String rearCamera;

    @Column(name = "c_pu")
    private String cPU;

    @Column(name = "g_pu")
    private String gPU;

    @Column(name = "r_am")
    private String rAM;

    @Column(name = "r_om")
    private String rOM;

    @Column(name = "o_s")
    private String oS;

    @Column(name = "pin")
    private String pin;

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

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getSupportSim() {
        return this.supportSim;
    }

    public Products supportSim(String supportSim) {
        this.setSupportSim(supportSim);
        return this;
    }

    public void setSupportSim(String supportSim) {
        this.supportSim = supportSim;
    }

    public String getMonitor() {
        return this.monitor;
    }

    public Products monitor(String monitor) {
        this.setMonitor(monitor);
        return this;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getColor() {
        return this.color;
    }

    public Products color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFrontCamera() {
        return this.frontCamera;
    }

    public Products frontCamera(String frontCamera) {
        this.setFrontCamera(frontCamera);
        return this;
    }

    public void setFrontCamera(String frontCamera) {
        this.frontCamera = frontCamera;
    }

    public String getRearCamera() {
        return this.rearCamera;
    }

    public Products rearCamera(String rearCamera) {
        this.setRearCamera(rearCamera);
        return this;
    }

    public void setRearCamera(String rearCamera) {
        this.rearCamera = rearCamera;
    }

    public String getcPU() {
        return this.cPU;
    }

    public Products cPU(String cPU) {
        this.setcPU(cPU);
        return this;
    }

    public void setcPU(String cPU) {
        this.cPU = cPU;
    }

    public String getgPU() {
        return this.gPU;
    }

    public Products gPU(String gPU) {
        this.setgPU(gPU);
        return this;
    }

    public void setgPU(String gPU) {
        this.gPU = gPU;
    }

    public String getrAM() {
        return this.rAM;
    }

    public Products rAM(String rAM) {
        this.setrAM(rAM);
        return this;
    }

    public void setrAM(String rAM) {
        this.rAM = rAM;
    }

    public String getrOM() {
        return this.rOM;
    }

    public Products rOM(String rOM) {
        this.setrOM(rOM);
        return this;
    }

    public void setrOM(String rOM) {
        this.rOM = rOM;
    }

    public String getoS() {
        return this.oS;
    }

    public Products oS(String oS) {
        this.setoS(oS);
        return this;
    }

    public void setoS(String oS) {
        this.oS = oS;
    }

    public String getPin() {
        return this.pin;
    }

    public Products pin(String pin) {
        this.setPin(pin);
        return this;
    }

    public void setPin(String pin) {
        this.pin = pin;
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
