package com.eoc900.classes;

import java.util.Arrays;

import java.util.Arrays;
import java.lang.Integer;

public class Multidimentional {
    // --------> EXPLANATION <-----------
    // This class only works with multidimentional arrays.
    // An item would be an array inside an array, which will have a unique
    // identifier value, i.e.

    // {{"a010","service bla bla"},{"a020","..."},{"a030","...."}}

    // {{a,b,c},{d,e,f},{g,h,i}} column 0 would look at ----> a,d,g
    public static java.lang.Integer findNeedleIndex(int columnIndex, String searchFor, String[][] searchInArr) {
        // the index is going to be used later to remove, update or add that array from
        // the searchInArr
        // System.out.println(columnIndex);
        // System.out.println(searchFor);
        // System.out.println(Arrays.deepToString(searchInArr));
        int len = searchInArr.length;
        for (int i = 0; i < len; i++) {
            if (searchInArr[i][columnIndex] == null) {
                continue;
            }
            if (searchInArr[i][columnIndex].equals(searchFor)) {
                return i;
            }
        }

        return null;
    }

    // -----> Add item when it doesn't exists
    public static String[][] addIfItemNotFound(String[] arr2Add, int onColumn, String searchFor,
            String[][] searchInArr) {

        // Add an array to the multidimentional array if doesn't exist
        java.lang.Integer index = Multidimentional.findNeedleIndex(onColumn,
                searchFor, searchInArr);

        if (index == null) {
            System.out.println("Item was'nt found");
            String[][] newArr = Multidimentional.popArray(arr2Add, searchInArr);
            System.out.println("Longitud de arreglo es el siguiente");
            System.out.println(newArr.length);
            return newArr;
        }

        // System.out.println("Item was found");
        return searchInArr;

    }

    // -----> It just pushes an array to a multidimentional array
    public static String[][] pushItemToArray(String[] arr2Add, String[][] pushHere) {

        String[][] newArr = new String[pushHere.length + 1][arr2Add.length];

        for (int i = 0; i < pushHere.length; i++) {
            newArr[i] = pushHere[i];
        }

        newArr[pushHere.length] = arr2Add;

        System.out.println(Arrays.deepToString(newArr));

        return newArr;
    }

    // -----> insert at the beginning of the array
    public static String[][] popArray(String[] arr2Add, String[][] pushHere) {
        String[][] newArr = new String[pushHere.length + 1][arr2Add.length];
        newArr[0] = arr2Add;
        for (int i = 0; i < pushHere.length; i++) {
            newArr[i + 1] = pushHere[i];
        }
        return newArr;
    }

    // -----> If there is no same item it has nothing to update
    // -----> so it returns null
    public static String[][] updateItemOnArray(int onColumn, String searchFor, String[] arr2Add,
            String[][] updateHere) {

        java.lang.Integer index = findNeedleIndex(onColumn, searchFor, updateHere);
        System.out.println(index);
        if (index == null) {
            return updateHere;
        }

        String[][] newArr = new String[updateHere.length][arr2Add.length];
        for (int i = 0; i < updateHere.length; i++) {
            if (i == index) {
                newArr[i] = arr2Add;
            }
            if (i != index) {
                newArr[i] = updateHere[i];
            }
        }

        return newArr;
    }

    // -----> Removes item if found
    public static String[][] removeItemFromArray(int onColumn, String searchFor,
            String[][] removeHere) {
        java.lang.Integer index = findNeedleIndex(onColumn, searchFor, removeHere);
        if (index == null) { // item not found, therefore nothing to remove
            return removeHere;
        }
        String[][] newArr = new String[removeHere.length][removeHere[0].length];
        int k = 0;
        for (int i = 0; i < removeHere.length; i++) {
            if (i != index) {
                newArr[k] = removeHere[i];
                k++;
                continue;
            }
        }
        return newArr;

    }

    public static String[][] removeArrayNullValues(String[][] arr, int itemLen) {
        int arrLen = arr.length;

        for (int i = 0; i < arrLen; i++) {
            if (arr[i][0] == null || arr[i][0].isEmpty()) {
                arrLen = i;
                break;
            }
        }
        String[][] newArr = new String[arrLen][itemLen];

        for (int j = 0; j < arrLen; j++) {
            newArr[j] = arr[j];
        }

        return newArr;
    }

    public static String[][] reduceArray(String[][] mainArray, String[] arrayOfIndexes) { // For the moment only 4 item
                                                                                          // array
        int lenOfItem = arrayOfIndexes.length;
        int lenOfBigArr = mainArray.length;
        String[][] updated = new String[lenOfBigArr][lenOfItem];
        for (int i = 0; i < lenOfBigArr; i++) {
            updated[i][0] = mainArray[i][Integer.parseInt(arrayOfIndexes[0])];
            updated[i][1] = mainArray[i][Integer.parseInt(arrayOfIndexes[1])];
            updated[i][2] = mainArray[i][Integer.parseInt(arrayOfIndexes[2])];
            updated[i][3] = mainArray[i][Integer.parseInt(arrayOfIndexes[3])];
        }
        return updated;
    }

    public static String splitWord(String word, int wordLimit) {
        String[] res = word.split(" ");
        String newWord = res[0] + " " + res[1];
        return newWord;
    }

}
