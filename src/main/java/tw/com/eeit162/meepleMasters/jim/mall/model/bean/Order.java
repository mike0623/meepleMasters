package tw.com.eeit162.meepleMasters.jim.mall.model.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "order")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderId")
	private Integer orderId;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "orderDate")
	private Date orderDate;

	@Column(name = "totalPrice")
	private Integer totalPrice;

	@Column(name = "orderStatus")
	private String orderStatus;

	@Column(name = "paymentMethod")
	private String paymentMethod;

	@Column(name = "fk_memberId")
	private Integer fkMemberId;

	@Column(name = "fk_cartId")
	private Integer fkCartId;

	@Column(name = "fk_receiver")
	private Integer fkReceiver;

	@PrePersist
	public void onCreate() {
		if (orderDate == null) {
			orderDate = new Date();
		}
	}

	public Order() {
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Integer getFkMemberId() {
		return fkMemberId;
	}

	public void setFkMemberId(Integer fkMemberId) {
		this.fkMemberId = fkMemberId;
	}

	public Integer getFkCartId() {
		return fkCartId;
	}

	public void setFkCartId(Integer fkCartId) {
		this.fkCartId = fkCartId;
	}

	public Integer getFkReceiver() {
		return fkReceiver;
	}

	public void setFkReceiver(Integer fkReceiver) {
		this.fkReceiver = fkReceiver;
	}

}
