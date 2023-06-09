package pt.ipvc.dal;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@NamedQueries({
        @NamedQuery(name = "orders.index", query = "SELECT orders FROM Orders orders"),
        @NamedQuery(name = "orders.getUserOrdersByUserId", query = "SELECT orders FROM Orders orders WHERE orders.idClient = :userId"),
        @NamedQuery(name = "orders.getTotal", query = "SELECT SUM(orders.orderPrice) FROM Orders orders"),
})
public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "id_stock")
    private int idStock;
    @Basic
    @Column(name = "id_client")
    private int idClient;
    @Basic
    @Column(name = "order_price")
    private double orderPrice;
    @Basic
    @Column(name = "order_quantity")
    private int orderQuantity;
    @Basic
    @Column(name = "date_start")
    private String dateStart;
    @Basic
    @Column(name = "state")
    private String state;
    @Basic
    @Column(name = "deleted_on")
    private Timestamp deletedOn;

    @ManyToOne
    @JoinColumn(name = "id_stock", referencedColumnName = "id", insertable = false, nullable = false, updatable = false)
    private Stock stockByIdStock;

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id", insertable = false, nullable = false, updatable = false)
    private Users clientByIdClient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdStock() {
        return idStock;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Timestamp deletedOn) {
        this.deletedOn = deletedOn;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orders orders = (Orders) o;

        if (id != orders.id) return false;
        if (idStock != orders.idStock) return false;
        if (idClient != orders.idClient) return false;
        if (Double.compare(orders.orderPrice, orderPrice) != 0) return false;
        if (orderQuantity != orders.orderQuantity) return false;
        if (dateStart != null ? !dateStart.equals(orders.dateStart) : orders.dateStart != null) return false;
        if (state != null ? !state.equals(orders.state) : orders.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + idStock;
        result = 31 * result + idClient;
        temp = Double.doubleToLongBits(orderPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + orderQuantity;
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    public Stock getStockByIdStock() {
        return stockByIdStock;
    }

    public void setStockByIdStock(Stock stockByIdStock) {
        this.stockByIdStock = stockByIdStock;
    }

    public Users getClientByIdClient() {
        return clientByIdClient;
    }

    public void setClientByIdClient(Users clientByIdClient) {
        this.clientByIdClient = clientByIdClient;
    }
}