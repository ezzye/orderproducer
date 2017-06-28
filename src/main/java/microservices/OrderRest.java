package microservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import microservices.Order;
import org.apache.commons.io.IOUtils;
//import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;



/**
 * Created by ellioe03 on 27/06/2017.
 */
@Named
@Path("/")


public class OrderRest {

    boolean stockServiceOnline =true;

    @Inject
    private RestTemplate restTemplate;

    private static List<Order> Orders = new ArrayList<Order>();
    static {
        Order order1 = new Order();
        order1.setOrderId(1);
        order1.setProductCode("Prod1");
        order1.setQty(1);
        Orders.add(order1);

        Order order2 = new Order();
        order2.setOrderId(2);
        order2.setProductCode("Prod2");
        order2.setQty(2);
        Orders.add(order2);

        Order order3 = new Order();
        order3.setOrderId(3);
        order3.setProductCode("Prod3");
        order3.setQty(3);
        Orders.add(order3);

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrders() {
        return Orders;
    }


    /****** MAIN ONE ******/
    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("processOrder")
    public ProcessOrderResponse processOrder(final ProcessOrderRequest req){
//        try{
//            PrintWriter writer = new PrintWriter("orders.txt", "UTF-8");
//            writer.println("The first line");
//            writer.println("The second line");
//            writer.close();
//        } catch (IOException e) {
//            // do something
//        }

        try {
            String jsonInString= null;
            ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try {
                jsonInString = writer.writeValueAsString(req);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("orders.txt", true)));
            out.println(jsonInString);
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }


        //        HttpEntity<String> myEnt = null;
//
//        JSONObject json;
//        try {
//            final String s = IOUtils.toString(new URL("https://graph.facebook.com/me"), Charset.forName("UTF-8"));
//            json = new JSONObject(s);
//            System.out.println(json);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
////        ResponseEntity<ProcessOrderResponse> orderResp;
////        if (stockServiceOnline) {
////            try {
////                orderResp = restTemplate.exchange(
////                        "http://localhost:8082/updateStock?orderId={orderId}&productCode={productCode}&qty={qty}", org.springframework.http.HttpMethod.GET, myEnt, ProcessOrderResponse.class, new Long(1), "Prod1", new Integer(2));
////            } catch (Exception e) {
////                System.out.print("Error blah blah");
////            }
////            System.out.print("sdfsdfsdfs");
////        } else {
////            return null;
////        }
//        return null;
//        String[] args = new String[0];
//
//        JmsTemplate jmsTemplate = Application.context.getBean(JmsTemplate.class);
//
//
//        System.out.println("Sending an email message.");
//        Stock stock1 = new Stock();
//        stock1.setProductCode("Prod1");
//        stock1.setQty(1);
////        Stocks.add(stock1);
//
//        jmsTemplate.convertAndSend("stock.queue", stock1.toString());
        return null;

    }


    @GET
    @Path("order")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrder(@QueryParam("id") long id) {
        Order or = null;
        for (Order o : Orders) {
            if (o.getOrderId() == id)
                or = o;
        }
        return or;
    }

    @XmlRootElement
    public static class ProcessOrderRequest {
//        @XmlElement
        String productCode;
//        @XmlElement
 Long orderId;
//        @XmlElement
        int qty;
        long date = System.currentTimeMillis();
        String eventId = UUID.randomUUID().toString();

        @Override
        public String toString() {
            ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try {
                String jsonInString = writer.writeValueAsString(this);
                return jsonInString;

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
//            return "ProcessOrderRequest{" +
//                    "productCode='" + productCode + '\'' +
//                    ", orderId=" + orderId +
//                    ", qty=" + qty +
//                    ", date=" + date +
//                    ", eventId=" + eventId +
//                    '}';
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }
    }


    public static class ProcessOrderResponse {
        private Long orderId;
        private String productCode;
        private Boolean success;

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }
    }
    public class Nathan{
       private  String name;
       private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
