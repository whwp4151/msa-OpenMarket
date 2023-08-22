package com.example.productservice.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.productservice.dto.ProductDto.CreateProductDto;
import com.example.productservice.dto.ProductDto.ProductOptionDto;
import com.example.productservice.dto.ProductDto.ProductResponseDto;
import com.example.productservice.dto.ProductDto.UpdateProductDto;
import com.example.productservice.dto.ProductDto.UpdateProductOptionDto;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveProduct() throws Exception {
        //given
        CreateProductDto dto = new CreateProductDto();
        dto.setCategoryId("1001");
        dto.setName("사랑의 물결 일기장 : 복숭아");
        dto.setBrandId(1L);
        dto.setPrice(35000);
        dto.setConsumerPrice(31500);
        dto.setDiscountedPrice(3500);
        dto.setIsSold(Boolean.TRUE);
        dto.setTotalStockQuantity(3000);

        ProductOptionDto dto1 = ProductOptionDto.builder()
            .name("그리드")
            .addPrice(3000)
            .stockQuantity(1000)
            .build();
        ProductOptionDto dto2 = ProductOptionDto.builder()
            .name("라인")
            .addPrice(0)
            .stockQuantity(1000)
            .build();
        ProductOptionDto dto3 = ProductOptionDto.builder()
            .name("무지")
            .addPrice(0)
            .stockQuantity(1000)
            .build();

        dto.setProductOptions(Arrays.asList(dto1, dto2, dto3));

        //when
        ProductResponseDto product = productService.createProduct(dto);

        //then
        assertEquals(dto.getName(), product.getName());
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void updateProductUpdateOption() throws Exception {
        //given
        UpdateProductDto dto = createUpdateProductDto();
        UpdateProductOptionDto optionDto1 = new UpdateProductOptionDto();
        optionDto1.setProductOptionId(2L);
        optionDto1.setName("그리드2");
        optionDto1.setAddPrice(30000);
        optionDto1.setStockQuantity(1002);

        dto.setProductOptions(List.of(optionDto1));

        //when
        ProductResponseDto product = productService.updateProduct(dto);

        //then
        assertEquals(dto.getName(), product.getName());
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void updateProductInsertOption() throws Exception {
        //given
        UpdateProductDto dto = createUpdateProductDto();
        UpdateProductOptionDto optionDto1 = new UpdateProductOptionDto();
        optionDto1.setName("그리드3");
        optionDto1.setAddPrice(40000);
        optionDto1.setStockQuantity(1004);

        dto.setProductOptions(List.of(optionDto1));

        //when
        ProductResponseDto product = productService.updateProduct(dto);

        //then
        assertEquals(dto.getName(), product.getName());
    }

    private UpdateProductDto createUpdateProductDto() {
        UpdateProductDto dto = new UpdateProductDto();
        dto.setProductId(1L);
        dto.setCategoryId("1002");
        dto.setName("사랑의 물결 일기장 : 복숭아2");
        dto.setPrice(35000);
        dto.setConsumerPrice(31500);
        dto.setDiscountedPrice(3500);
        dto.setIsSold(Boolean.TRUE);
        dto.setTotalStockQuantity(3000);
        return dto;
    }

}