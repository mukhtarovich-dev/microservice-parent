package com.mukhtarovich.uz.product_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mukhtarovich.uz.product_service.dto.ProductRequest;
import com.mukhtarovich.uz.product_service.model.Product;
import com.mukhtarovich.uz.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBContainer;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
//    @BeforeEach
//    void setup() {
//        productRepository.deleteAll();
//    }

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString))
                .andExpect(status().isCreated());
//        Assertions.assertEquals(1, productRepository.findAll().size());
    }

    @Test
    void shouldUpdateProduct() throws Exception {

        Product product = productRepository.save(
                Product.builder()
                        .name("Iphone 14")
                        .description("Old description")
                        .price(BigDecimal.valueOf(1000))
                        .build()
        );

        ProductRequest request = new ProductRequest(
                "Iphone 15",
                "Updated description",
                BigDecimal.valueOf(1200)
        );

        mockMvc.perform(MockMvcRequestBuilders.put(
                                "/api/product/{id}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Iphone 15"))
                .andExpect(jsonPath("$.data.price").value(1200));
    }



    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Iphone 17 Pro max")
                .description("Iphone 17 Pro max")
                .price(BigDecimal.valueOf(1200))
                .build();
    }


}
