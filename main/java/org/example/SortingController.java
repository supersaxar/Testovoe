package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class SortingController {
    private final JdbcTemplate jdbcTemplate;
    private final SortingService sortingService;
    private final AtomicLong sortingIdGenerator = new AtomicLong();

    @Autowired
    public SortingController(JdbcTemplate jdbcTemplate, SortingService sortingService) {
        this.jdbcTemplate = jdbcTemplate;
        this.sortingService = sortingService;
    }

    @PostMapping("/sort")
    public Map<String, Object> sort(@RequestBody Map<String, int[]> request) {
        int[] numbers = request.get("numbers");
        int[] sorted = sortingService.bubbleSort(numbers);

        long sortingId = sortingIdGenerator.incrementAndGet();

        for (int i = 0; i < sorted.length; i++) {
            jdbcTemplate.update(
                    "INSERT INTO sorting_results (sorting_id, position, value) VALUES (?, ?, ?)",
                    sortingId, i, sorted[i]
            );
        }

        return Map.of(
                "sortingId", sortingId,
                "sortedArray", sorted
        );
    }

    @GetMapping("/sort/{id}")
    public int[] getSortedArray(@PathVariable long id) {
        List<Integer> sorted = jdbcTemplate.query(
                "SELECT value FROM sorting_results WHERE sorting_id = ? ORDER BY position",
                (rs, rowNum) -> rs.getInt("value"),
                id
        );

        return sorted.stream().mapToInt(Integer::intValue).toArray();
    }
}