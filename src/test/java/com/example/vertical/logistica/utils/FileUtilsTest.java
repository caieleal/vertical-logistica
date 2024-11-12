package com.example.vertical.logistica.utils;

import com.example.vertical.logistica.core.domain.model.FileInputData;
import com.example.vertical.logistica.core.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileUtilsTest {
    private static final String LINE = "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308";

    @Test
    public void testParseLong() {
        Long result = FileUtils.parseLong(LINE, 0, 10);
        assertEquals(70L, result);
    }

    @Test
    public void testParseString() {
        String result = FileUtils.parseString(LINE, 10, 55);
        assertEquals("Palmer Prosacco", result);
    }

    @Test
    public void testParseBigDecimal() {
        BigDecimal result = FileUtils.parseBigDecimal(LINE, 75, 87);
        assertEquals(new BigDecimal("1836.74"), result);
    }

    @Test
    public void testParseDate() {
        LocalDate result = FileUtils.parseDate(LINE, 87, 95);
        assertEquals(LocalDate.of(2021, 3, 8), result);
    }

    @Test
    public void testReadLine() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "file.txt", "text/plain", LINE.getBytes());

        Stream<String> lines = FileUtils.readLine(file);
        List<String> result = lines.toList();

        assertEquals(1, result.size());
        assertEquals(LINE, result.get(0));
    }

    @Test
    public void testGroupByUserName() {
        FileInputData.User user1 = new FileInputData.User(1L, "Palmer Prosacco");
        FileInputData.User user2 = new FileInputData.User(2L, "Brenton Orn");

        FileInputData data1 = new FileInputData();
        data1.setUser(user1);

        FileInputData data2 = new FileInputData();
        data2.setUser(user1);

        FileInputData data3 = new FileInputData();
        data3.setUser(user2);

        List<FileInputData> filesInputData = List.of(data1, data2, data3);

        Map<String, List<FileInputData>> result = FileUtils.groupByUserName(filesInputData);

        assertEquals(2, result.size());
        assertEquals(1, result.get("Brenton Orn").size());
        assertEquals(2, result.get("Palmer Prosacco").size());
    }
}
