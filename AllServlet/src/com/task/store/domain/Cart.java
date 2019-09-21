package com.task.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//两个属性3个方法
public class Cart {
	// 总计/积分
	private double total = 0;
	Map<String, CartItem> map = new HashMap<String, CartItem>();

	// 添加购物项到购物车
	public void addCartItemToCart(CartItem cartItem) {
		String pid = cartItem.getProduct().getPid();
		if (map.containsKey(pid)) {
			CartItem oldItem = map.get(pid);
			oldItem.setNum(map.get(pid).getNum() + cartItem.getNum());
		} else {
			map.put(pid, cartItem);
		}
	}

	// 移除购物项
	public void removeCartItem(String pid) {
		map.remove(pid);
	}

	// 清空购物车
	public void clearCart() {
		map.clear();
	}

	public Collection<CartItem> getCartItems() {
		return map.values();
	}

	public double getTotal() {
		total = 0;
		Collection<CartItem> values = map.values();
		for (CartItem cartItem : values) {
			total += cartItem.getSubTotal();
		}
		return total;
	}

	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
}
