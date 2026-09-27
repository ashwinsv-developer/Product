package com.product.data.mapper

import com.product.data.model.Product as DataProduct
import com.product.domain.model.Product as DomainProduct

/**
 * Mapper to transform Product data models/DTOs into clean Domain models.
 */
fun DataProduct.toDomain(): DomainProduct {
    return DomainProduct(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        discountPercentage = this.discountPercentage,
        rating = this.rating,
        stock = this.stock,
        brand = this.brand,
        category = this.category,
        thumbnail = this.thumbnail,
        images = this.images
    )
}
