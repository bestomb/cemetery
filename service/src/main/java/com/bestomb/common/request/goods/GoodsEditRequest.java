package com.bestomb.common.request.goods;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class GoodsEditRequest {

    private String id; // 商品ID
    private String name;
    private BigDecimal price;
    private String images; // 图片json信息
    private String type; // 商品类型 （1：陵园装饰主题、2：陵园、3：陵园存储、4：交易币、5：动物、6：植物）
    private String description; // 详细描述
    private String extendAttribute; // 商品扩展属性
    private MultipartFile imageFile;    //商品图片文件流对象

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtendAttribute() {
        return extendAttribute;
    }

    public void setExtendAttribute(String extendAttribute) {
        this.extendAttribute = extendAttribute;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}