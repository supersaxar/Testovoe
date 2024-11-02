package org.example;

import org.springframework.stereotype.Service;

@Service
public class SortingService {
    public int[] bubbleSort(int[] arr) {
        int[] result = arr.clone();
        int n = result.length;

        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (result[j] > result[j+1]) {
                    int temp = result[j];
                    result[j] = result[j+1];
                    result[j+1] = temp;
                }
            }
        }

        return result;
    }
}