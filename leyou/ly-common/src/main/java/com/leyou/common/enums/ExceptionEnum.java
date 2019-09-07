package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    PRICE_CANNOT_BE_NULL(400,"价格不可为空"),
    ID_CANNOT_BE_NULL(400,"ID不可为空"),
    CATEGORY_NOY_FOUND(404,"商品分类没查到"),
    BRAND_NOT_FOUND(404,"品牌不存在"),
    BRAND_SAVE_ERROR(500,"新增商品失败"),
    UPLOAD_FILE_ERROR(500,"文件上传失败"),
    INVALID_FILE_TYPE(400,"无效的文件类型"),
    GROUP_NOT_FOUND(404,"商品规格组没查到"),
    PARA_NOT_FOUND(404,"商品规格参数没查到"),
    GOODS_NOT_EXIST(404,"商品不存在"),
    GOODS_NOT_CREATE(500,"商品创建不了")
    ;
    private int code;
    private String msg;

}
