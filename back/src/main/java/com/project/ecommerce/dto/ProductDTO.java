package com.project.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ecommerce.model.InventoryStatus;
import java.time.LocalDateTime;
import lombok.Builder;

/**
 * Data Transfer Object for Product
 * I added DTO on Product only, not on cart or wishlist, to highlight the usage of DTOs in the project
 * @param id
 * @param code
 * @param name
 * @param description
 * @param image
 * @param category
 * @param price
 * @param quantity
 * @param internalReference
 * @param shellId
 * @param inventoryStatus
 * @param rating
 * @param createdAt
 * @param updatedAt
 */
@Builder
public record ProductDTO(
    Long id,
    @JsonProperty("code") String code,
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("image") String image,
    @JsonProperty("category") String category,
    @JsonProperty("price") Double price,
    @JsonProperty("quantity") Integer quantity,
    @JsonProperty("internal_reference") String internalReference,
    @JsonProperty("shell_id") Long shellId,
    @JsonProperty("inventory_status") InventoryStatus inventoryStatus,
    @JsonProperty("rating") Integer rating,

    @JsonProperty("created_at") LocalDateTime createdAt,
    @JsonProperty("updated_at") LocalDateTime updatedAt
) {}
