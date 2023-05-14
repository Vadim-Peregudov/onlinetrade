package my.shop.onlinetrade.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import my.shop.onlinetrade.dto.FilterParamDto;
import my.shop.onlinetrade.dto.FilterRequestDto;
import my.shop.onlinetrade.entity.*;
import my.shop.onlinetrade.repository.custom.CustomProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ProductRepositoryImpl extends SimpleJpaRepository<Product, Long> implements CustomProductRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public ProductRepositoryImpl(EntityManager entityManager) {
        super(Product.class, entityManager);
        this.entityManager = entityManager;
    }

    public Page<Product> searchProducts(FilterRequestDto filterRequestDto, int pageSize) {

        String modelName = filterRequestDto.getModelName();
        String categoryName = filterRequestDto.getCategoryName();
        List<FilterParamDto> filterParams = filterRequestDto.getFilterParam();
        String sort = filterRequestDto.getSort();
        int page = filterRequestDto.getPage() - 1;


        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        Join<Product, Category> categoryJoin = product.join("category");
        Predicate categoryPredicate = builder.equal(categoryJoin.get("name"), categoryName);

        Predicate modelPredicate = builder.equal(product.get("name"), modelName);

        List<Predicate> filterParamPredicates = new ArrayList<>();
        for (FilterParamDto filterParam : filterParams) {
            String featureName = filterParam.getName();
            String featureValue = filterParam.getValue();

            Join<Product, ProductFeatures> productFeaturesJoin = product.join("productFeatures");
            Join<ProductFeatures, Features> featuresJoin = productFeaturesJoin.join("features");

            Predicate featurePredicate = builder.and(
                    builder.equal(featuresJoin.get("name"), featureName),
                    builder.equal(productFeaturesJoin.get("value"), featureValue)
            );

            filterParamPredicates.add(featurePredicate);
        }

        Predicate filterParamsPredicate = builder.and(filterParamPredicates.toArray(new Predicate[0]));

        Join<Product, ProductImage> productImageJoin = product.join("productImages");
        Predicate imagePredicate = builder.isNotNull(productImageJoin.get("id"));

        Predicate finalPredicate = builder.and(categoryPredicate, modelPredicate, filterParamsPredicate, imagePredicate);
        query.where(finalPredicate);

        switch (sort) {
            case "price-asc" -> query.orderBy(builder.asc(product.get("price")));
            case "price-desc" -> query.orderBy(builder.desc(product.get("price")));
            case "name-asc" -> query.orderBy(builder.asc(product.get("fullName")));
            case "name-desc" -> query.orderBy(builder.desc(product.get("fullName")));
            default -> query.orderBy(builder.desc(product.get("id")));
        }

        TypedQuery<Product> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(page * pageSize);
        typedQuery.setMaxResults(pageSize);

/*
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        countQuery.select(builder.countDistinct(product));
        countQuery.where(finalPredicate);

        TypedQuery<Long> countTypedQuery = entityManager.createQuery(countQuery);
        Long totalCount1 = countTypedQuery.getSingleResult();
        System.out.println(totalCount1);
 */
        long totalCount = countProduct(filterRequestDto);
        Long totalPages = (long) Math.ceil(totalCount / pageSize);

        return new PageImpl(typedQuery.getResultList(), PageRequest.of(page, pageSize), totalPages);
    }

    private Long countProduct(FilterRequestDto filterRequestDto) {

        String modelName = filterRequestDto.getModelName();
        String categoryName = filterRequestDto.getCategoryName();
        List<FilterParamDto> filterParams = filterRequestDto.getFilterParam();
        String sort = filterRequestDto.getSort();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Product> product = query.from(Product.class);

        Join<Product, Category> categoryJoin = product.join("category");
        Predicate categoryPredicate = builder.equal(categoryJoin.get("name"), categoryName);

        Predicate modelPredicate = builder.equal(product.get("name"), modelName);

        List<Predicate> filterParamPredicates = new ArrayList<>();
        for (FilterParamDto filterParam : filterParams) {
            String featureName = filterParam.getName();
            String featureValue = filterParam.getValue();

            Join<Product, ProductFeatures> productFeaturesJoin = product.join("productFeatures");
            Join<ProductFeatures, Features> featuresJoin = productFeaturesJoin.join("features");

            Predicate featurePredicate = builder.and(
                    builder.equal(featuresJoin.get("name"), featureName),
                    builder.equal(productFeaturesJoin.get("value"), featureValue)
            );

            filterParamPredicates.add(featurePredicate);
        }

        Predicate filterParamsPredicate = builder.and(filterParamPredicates.toArray(new Predicate[0]));

        Join<Product, ProductImage> productImageJoin = product.join("productImages");
        Predicate imagePredicate = builder.isNotNull(productImageJoin.get("id"));

        Predicate finalPredicate = builder.and(categoryPredicate, modelPredicate, filterParamsPredicate, imagePredicate);
        query.where(finalPredicate);

        switch (sort) {
            case "price-asc" -> query.orderBy(builder.asc(product.get("price")));
            case "price-desc" -> query.orderBy(builder.desc(product.get("price")));
            case "name-asc" -> query.orderBy(builder.asc(product.get("fullName")));
            case "name-desc" -> query.orderBy(builder.desc(product.get("fullName")));
            default -> query.orderBy(builder.desc(product.get("id")));
        }
        query.select(builder.countDistinct(product));
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);

        return typedQuery.getSingleResult();
    }


}


