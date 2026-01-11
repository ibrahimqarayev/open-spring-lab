    package com.openspringlab.ecommerce.mapper;

    import com.openspringlab.ecommerce.dto.product.CreateProductRequest;
    import com.openspringlab.ecommerce.dto.product.ProductResponse;
    import com.openspringlab.ecommerce.dto.product.UpdateProductRequest;
    import com.openspringlab.ecommerce.model.Category;
    import com.openspringlab.ecommerce.model.Product;
    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;
    import org.mapstruct.Named;
    import org.mapstruct.ReportingPolicy;

    @Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface ProductMapper {

        Product toEntity(CreateProductRequest createProductRequest);

        Product toEntity(UpdateProductRequest updateProductRequest);

        @Mapping(source = "category",target = "categoryId",qualifiedByName = "getCategoryId")
        ProductResponse toDto(Product product);

        @Named("getCategoryId")
        private Long getCategoryId(Category category){
            return category.getId();
        }


    }
