package com.openspringlab.ecommerce.mapper;

import com.openspringlab.ecommerce.dto.category.CategoryResponse;
import com.openspringlab.ecommerce.dto.category.CreateCategoryRequest;
import com.openspringlab.ecommerce.dto.category.UpdateCategoryRequest;
import com.openspringlab.ecommerce.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category toEntity(CreateCategoryRequest createCategoryRequest);

    Category toEntity(UpdateCategoryRequest updateCategoryRequest);

    CategoryResponse toDto(Category category);
}
