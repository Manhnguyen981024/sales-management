package com.demo.productservice.dto;

import com.demo.productservice.validation.OnCreate;
import com.demo.productservice.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
    private Long id;

    @Schema(description = "Tên sản phẩm", example = "Giày Nike Air")
    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Product name can not be null!")
    private String name;

    @Schema(description = "Mô tả sản phẩm", example = "Giày Nike Air")
    @NotNull(groups = OnCreate.class, message = "Description can not be null!")
    private String description;

    @Schema(description = "Giá của sản phẩm", example = "500000")
    @NotNull (groups = {OnCreate.class, OnUpdate.class}, message = "Price of Product can not be null!")
    @Positive (groups = {OnCreate.class, OnUpdate.class}, message = "Price must be positive number!")
    private BigDecimal price;

    @Schema(description = "Số lượng sản phẩm", example = "500")
    @NotNull (groups = OnCreate.class, message = "Quantity of Product can not be null!")
    private Integer quantity;

    @Schema(description = "Trạng thái sản phẩm", example = "ACTIVE")
    @NotNull (groups = OnCreate.class, message = "Status of Product can not be null!")
    private String status;

    @Schema(description = "Hình ảnh sản phẩm", example = "hinh.jpg")
    @NotNull (groups = OnCreate.class, message = "Thumbnail of Product can not be null!")
    private String thumbnail;
}
