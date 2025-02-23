package bruno.project.orderms.listener;

import bruno.project.orderms.config.RabbirMqConfig;
import bruno.project.orderms.dto.OrderCreatedEvent;
import bruno.project.orderms.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final OrderService orderService;

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    public OrderCreatedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbirMqConfig.ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEvent> message){
        logger.info("Message consumed: {}", message);

        orderService.save(message.getPayload());
    }
}
