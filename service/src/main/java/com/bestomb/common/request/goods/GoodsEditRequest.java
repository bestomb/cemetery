package com.bestomb.common.request.goods;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class GoodsEditRequest {

    private String id; // 商品ID
    private String name;
    private BigDecimal price;
    private String images; // 图片json信息
    private String type; // 商品类型 （1：大门、2：墓碑、3：祭品（香）、4：祭品（蜡烛）、5：祭品（花）、6：普通祭品、7：扩展陵园存储容量、8：增加可建陵园数、9：动物饲料、10：植物肥料、11：祭品（广场）、12：祭品（湖泊）、13：祭品（盆栽植物）、14：祭品（食品）、15：祭品（用品）、16：祭品（金钱）、17：祭品（特色）、18：祭品（守护）、19:：祭品（休闲娱乐）、20：祭品（儿童用品））
    private String lifecycle;//生命周期
    private String description; // 详细描述
    private String extendAttribute; // 商品扩展属性
    private MultipartFile imageFile;    //商品图片文件流对象
    private Integer secondClassifyId;     //二级分类

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

    public String getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(String lifecycle) {
        this.lifecycle = lifecycle;
    }

    public Integer getSecondClassifyId() {
        return secondClassifyId;
    }

    public void setSecondClassifyId(Integer secondClassifyId) {
        this.secondClassifyId = secondClassifyId;
    }
}
