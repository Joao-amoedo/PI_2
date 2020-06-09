package com.example.vprojetos.utils;

import com.example.vprojetos.model.Projeto;

public class QuickSort {

    public static final int COMPARACAO_MEDIA = 0;
    public static final int COMPARACAO_DATA_CRIACAO = 1;
    private int compara;

    public QuickSort(int compara) {
        if (compara == COMPARACAO_MEDIA || compara == COMPARACAO_DATA_CRIACAO) {
            this.compara = compara;

        } else
            throw new RuntimeException();

    }

    int partition(Projeto arr[], int low, int high) {
        Projeto pivot = arr[high];
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than the pivot
            if (compara == COMPARACAO_MEDIA) {
                i = comparacaoMedia(arr, pivot, i, j);
            } else if (compara == COMPARACAO_DATA_CRIACAO) {
                i = comparacaoDataCriacao(arr, pivot, i, j);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        Projeto temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }


    private int comparacaoDataCriacao(Projeto[] arr, Projeto pivot, int i, int j) {
        if (arr[j].getDataInicio() < pivot.getDataInicio()) {
            i++;

            // swap arr[i] and arr[j]
            Projeto temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return i;
    }

    private int comparacaoMedia(Projeto[] arr, Projeto pivot, int i, int j) {
        if (arr[j].mediaNotas() < pivot.mediaNotas()) {
            i++;

            // swap arr[i] and arr[j]
            Projeto temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return i;
    }


    /* The main function that implements QuickSort()
    arr[] --> Array to be sorted,
    low --> Starting index,
    high --> Ending index */
    public void sort(Projeto arr[], int low, int high) {
        if (low < high) {
			/* pi is partitioning index, arr[pi] is
			now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }
}