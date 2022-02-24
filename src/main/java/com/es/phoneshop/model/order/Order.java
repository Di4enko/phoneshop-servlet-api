package com.es.phoneshop.model.order;

import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.cart.Cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Order extends Cart implements Serializable {
    private String secureID;
    private BigDecimal subtotal;
    private BigDecimal deliveryCost;
    private String firstName;
    private String lastName;
    private String phone;
    private String deliveryAddress;
    private LocalDate deliveryDate;
    private PaymentMethod paymentMethod;

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setSecureID(String secureID) {
        this.secureID = secureID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getSecureID() {
        return secureID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phone, deliveryAddress, deliveryDate, paymentMethod);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return subtotal.equals(order.subtotal)
                && deliveryCost.equals(deliveryCost)
                && firstName.equals(order.firstName)
                && lastName.equals(order.lastName)
                && phone.equals(order.phone)
                && deliveryAddress.equals(order.deliveryAddress)
                && deliveryDate.equals(order.deliveryDate)
                && paymentMethod.equals(order.paymentMethod);
    }
}
