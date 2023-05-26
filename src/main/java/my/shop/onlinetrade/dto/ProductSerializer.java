package my.shop.onlinetrade.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import my.shop.onlinetrade.entity.Product;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProductSerializer extends JsonSerializer<Product> {
    @Override
    public void serialize(Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject(); // Начало сериализации объекта Product

        jsonGenerator.writeObjectField("id", product.getId());
        jsonGenerator.writeObjectField("name", product.getName());
        jsonGenerator.writeObjectField("fullName", product.getFullName());
        jsonGenerator.writeObjectField("description", product.getDescription());
        jsonGenerator.writeObjectField("price", product.getPrice());
        jsonGenerator.writeObjectField("quantity", product.getQuantity());
        jsonGenerator.writeObjectField("category", product.getCategory());
        jsonGenerator.writeObjectField("productFeatures", product.getProductFeatures());
        jsonGenerator.writeObjectField("productImages", product.getProductImages());

        jsonGenerator.writeEndObject();
    }
}
