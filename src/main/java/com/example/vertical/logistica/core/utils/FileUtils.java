package com.example.vertical.logistica.core.utils;

import com.example.vertical.logistica.core.domain.model.FileInputData;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileUtils {
    private static final Pattern LEADING_ZERO_PATTERN = Pattern.compile("^0+(?!$)");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static Long parseLong(String line, int beginIndex, int endIndex){
        return Long.parseLong(leadingZeros(getString(line, beginIndex, endIndex)));
    }
    public static String parseString(String line, int beginIndex, int endIndex){
        return leadingZeros(getString(line, beginIndex, endIndex).stripLeading());
    }
    public static BigDecimal parseBigDecimal(String line, int beginIndex, int endIndex){
        return new BigDecimal((getString(line, beginIndex, endIndex).stripLeading()));
    }
    public static LocalDate parseDate(String line, int beginIndex, int endIndex){
        return LocalDate.parse(getString(line, beginIndex, endIndex), FORMATTER);
    }
    public static String leadingZeros(String line){
        return LEADING_ZERO_PATTERN.matcher(line).replaceAll("");
    }

    public static Stream<String> readLine(MultipartFile file) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getByteArrayInputStream(file)));
        return reader.lines();
    }

    public static Map<String, List<FileInputData>> groupByUserName(List<FileInputData> filesInputData){
        return filesInputData.stream().collect(Collectors.groupingBy(f -> f.getUser().getUserName()));
    }

    private static String getString(String line, int beginIndex, int endIndex) {
        return line.substring(beginIndex, endIndex);
    }

    private static ByteArrayInputStream getByteArrayInputStream(MultipartFile f) {
        ByteArrayInputStream stream;
        try {
            stream = new ByteArrayInputStream(f.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stream;
    }
}
