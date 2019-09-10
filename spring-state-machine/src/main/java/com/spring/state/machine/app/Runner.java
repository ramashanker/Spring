package com.spring.state.machine.app;

import java.util.Date;
import java.util.UUID;

import org.apache.catalina.servlets.DefaultServlet.SortManager.Order;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.spring.state.machine.event.OrderEvents;
import com.spring.state.machine.event.OrderStates;

import lombok.extern.java.Log;

@Log
@Component
class Runner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Order order = this.orderService.create(new Date());

		StateMachine<OrderStates, OrderEvents> paymentStateMachine = orderService.pay(order.getId(), UUID.randomUUID().toString());
		log.info("after calling pay(): " + paymentStateMachine.getState().getId().name());
		log.info("order: " + orderService.byId(order.getId()));

		StateMachine<OrderStates, OrderEvents> fulfilledStateMachine = orderService.fulfill(order.getId());
		log.info("after calling fulfill(): " + fulfilledStateMachine.getState().getId().name());
		log.info("order: " + orderService.byId(order.getId()));


	}

	private final OrderService orderService;

	Runner(OrderService orderService) {
		this.orderService = orderService;
	}
}