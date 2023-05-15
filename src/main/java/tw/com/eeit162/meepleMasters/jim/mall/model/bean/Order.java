package tw.com.eeit162.meepleMasters.jim.mall.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;

@Entity
@Table(name = "[order]")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderId")
	private Integer orderId;

	@JsonFormat(pattern = "yyyy-MM-dd")
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

	@JoinColumn(name = "fk_memberId", referencedColumnName = "memberId", nullable = false)
	@ManyToOne
	private Member member;

	@JoinColumn(name = "fk_receiver", referencedColumnName = "memberId")
	@ManyToOne
	private Member receiver;

	@JsonIgnore
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderDetail> OrderDetails;

	@PrePersist
	public void onCreate() {
		if (orderDate == null) {
			orderDate = new Date();
		}
		if (orderStatus == null) {
			orderStatus = "未付款";
		}
		if (paymentMethod == null) {
			paymentMethod = "尚未選擇";
		}
	}

	public Order() {
	}

	public Order(Member member) {
		this.member = member;
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Member getReceiver() {
		return receiver;
	}

	public void setReceiver(Member receiver) {
		this.receiver = receiver;
	}

	public List<OrderDetail> getOrderDetails() {
		return OrderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		OrderDetails = orderDetails;
	}

}
