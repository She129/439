package com.task.store.domain;

public class OrderItem {
	private String itemid;
	private int quantity;
	private double total;
	private Product product;
	private Order order;

	public OrderItem() {
		super();
	}

	public OrderItem(String itemid, int quantity, double total, Product product, Order order) {
		super();
		this.itemid = itemid;
		this.quantity = quantity;
		this.total = total;
		this.product = product;
		this.order = order;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderItem [itemid=" + itemid + ", quantity=" + quantity + ", total=" + total + ", product=" + product
				+ ", order=" + order + "]";
	}

}
