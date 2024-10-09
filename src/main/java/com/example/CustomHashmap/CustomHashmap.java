package com.example.CustomHashmap;



import java.nio.ByteBuffer;
import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class CustomHashmap {
    private byte[] byteArray;
    private int pageSize;
    private int numPages;

    public void init(int pageSize, int numPages) {
        this.pageSize = pageSize;
        this.numPages = numPages;
        this.byteArray = new byte[18 + pageSize * numPages];
        System.arraycopy(ByteBuffer.allocate(4).putInt(pageSize).array(), 0, byteArray, 0, 4);
        System.arraycopy(ByteBuffer.allocate(4).putInt(numPages).array(), 0, byteArray, 4, 4);
    }

    public int get(int key) {
        if (key == -1) return getSpecialKey(8, 9);
        if (key == 0) return getSpecialKey(13, 14);
        return getFromPage(key);
    }

    public void put(int key, int value) {
        switch (key) {
            case -1:
                putSpecialKey(8, 9, value);
                break;
            case 0:
                putSpecialKey(13, 14, value);
                break;
            default:
                putInPage(key, value);
                break;
        }
    }

    public void delete(int key) {
        if (key == -1) deleteSpecialKey(8, 9);
        else if (key == 0) deleteSpecialKey(13, 14);
        else deleteFromPage(key);
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append(byteToHex(byteArray, 0, 4)).append(" ")
          .append(byteToHex(byteArray, 4, 4)).append(" ")
          .append(byteToHex(byteArray, 8, 1)).append(" ")
          .append(byteToHex(byteArray, 9, 4)).append(" ")
          .append(byteToHex(byteArray, 13, 1)).append(" ")
          .append(byteToHex(byteArray, 14, 4)).append(" ");
        for (int i = 18; i < byteArray.length; i += pageSize) {
            sb.append("[");
            for (int j = i; j < i + pageSize; j += 8) {
                sb.append(byteToHex(byteArray, j, 4)).append(":")
                  .append(byteToHex(byteArray, j + 4, 4));
                if (j + 8 < i + pageSize) sb.append(",");
            }
            sb.append("] ");
        }
        return sb.toString().trim();
    }

    private int getSpecialKey(int flagIndex, int valueIndex) {
        if (byteArray[flagIndex] == 1) return ByteBuffer.wrap(byteArray, valueIndex, 4).getInt();
        return 0;
    }

    private void putSpecialKey(int flagIndex, int valueIndex, int value) {
        byteArray[flagIndex] = 1;
        System.arraycopy(ByteBuffer.allocate(4).putInt(value).array(), 0, byteArray, valueIndex, 4);
    }

    private void deleteSpecialKey(int flagIndex, int valueIndex) {
        byteArray[flagIndex] = 0;
        Arrays.fill(byteArray, valueIndex, valueIndex + 4, (byte) 0);
    }

    private int getFromPage(int key) {
        int pageIndex = (key % numPages) * pageSize + 18;
        for (int i = pageIndex; i < pageIndex + pageSize; i += 8) {
            int storedKey = ByteBuffer.wrap(byteArray, i, 4).getInt();
            if (storedKey == key) return ByteBuffer.wrap(byteArray, i + 4, 4).getInt();
            if (storedKey == 0) return 0;
        }
        return 0;
    }

    private void putInPage(int key, int value) {
        int pageIndex = (key % numPages) * pageSize + 18;
        for (int i = pageIndex; i < pageIndex + pageSize; i += 8) {
            int storedKey = ByteBuffer.wrap(byteArray, i, 4).getInt();
            if (storedKey == key) {
                System.arraycopy(ByteBuffer.allocate(4).putInt(value).array(), 0, byteArray, i + 4, 4);
                return;
            }
            if (storedKey == 0 || storedKey == -1) {
                System.arraycopy(ByteBuffer.allocate(4).putInt(key).array(), 0, byteArray, i, 4);
                System.arraycopy(ByteBuffer.allocate(4).putInt(value).array(), 0, byteArray, i + 4, 4);
                return;
            }
        }
        throw new IllegalStateException("No space in page");
    }

    private void deleteFromPage(int key) {
        int pageIndex = (key % numPages) * pageSize + 18;
        boolean found = false;
        for (int i = pageIndex; i < pageIndex + pageSize; i += 8) {
            int storedKey = ByteBuffer.wrap(byteArray, i, 4).getInt();
            if (storedKey == key) {
                ByteBuffer.wrap(byteArray, i, 8).putInt(0);
                found = true;
                break;
            }
        }
        if (!found) {
            boolean marked = false;
            for (int i = pageIndex; i < pageIndex + pageSize; i += 8) {
                if (!marked) {
                    ByteBuffer.wrap(byteArray, i, 8).putInt(-1);
                    marked = true;
                } else {
                    ByteBuffer.wrap(byteArray, i, 8).putInt(0);
                }
            }
        }
    }

    private String byteToHex(byte[] bytes, int offset, int length) {
        StringBuilder hex = new StringBuilder();
        for (int i = offset; i < offset + length; i++) {
            hex.append(String.format("%02x", bytes[i]));
        }
        return hex.toString();
    }
}
