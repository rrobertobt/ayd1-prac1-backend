package ayd.back.taller.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartCatalogResponseDto {
    private Integer id;
    private Integer supplierId;
    private Integer partId;
    private String partCode;
    private String partName;
    private Double price;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
