package com.example.vertical.logistica.core.utils;

import com.example.vertical.logistica.core.domain.model.FileInputData;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FileBuilder {
    private static final int USER_ID_BEGIN = 0;
    private static final int USER_ID_END = 10;
    private static final int NAME_BEGIN = 10;
    private static final int NAME_END = 55;
    private static final int ORDER_ID_BEGIN = 55;
    private static final int ORDER_ID_END = 65;
    private static final int PRODUCT_ID_BEGIN = 65;
    private static final int PRODUCT_ID_END = 75;
    private static final int VALUE_BEGIN = 75;
    private static final int VALUE_END = 87;
    private static final int DATE_BEGIN = 87;
    private static final int DATE_END = 95;

    public List<FileInputData> toFileInputData(List<MultipartFile> files) {
        return files.stream()
                .flatMap(file -> FileUtils.readLine(file)
                        .filter(Objects::nonNull)
                        .map(this::toFileInputData)
                )
                .sorted(Comparator.comparing(FileInputData::getOrderId))
                .collect(Collectors.toList());
    }
    private FileInputData toFileInputData(String line) {
        return FileInputData.builder()
                .orderId(FileUtils.parseLong(line, ORDER_ID_BEGIN, ORDER_ID_END))
                .user(toUserFile(line))
                .date(FileUtils.parseDate(line, DATE_BEGIN, DATE_END))
                .product(toProductFile(line))
                .build();
    }
    public FileInputData.User toUserFile(String line){
        return FileInputData.User.builder()
                .userId(FileUtils.parseLong(line, USER_ID_BEGIN, USER_ID_END))
                .userName(FileUtils.parseString(line, NAME_BEGIN, NAME_END))
                .build();
    }
    public FileInputData.Product toProductFile(String line){
        return FileInputData.Product.builder()
                .productId(FileUtils.parseLong(line, PRODUCT_ID_BEGIN, PRODUCT_ID_END))
                .value(FileUtils.parseBigDecimal(line, VALUE_BEGIN, VALUE_END))
                .build();
    }

}
