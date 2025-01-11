package ru.akvine.profiley;

import java.util.Random;

public class RandomGenerator {
    private final Random random = new Random();

    private static final int DEFAULT_STRING_LENGTH = 7;
    private static final int DEFAULT_BYTE_ARRAY_LENGTH = 10;

    public String generateRandomString() {
        return generateRandomString(DEFAULT_STRING_LENGTH);
    }

    /**
     * Генерирует случайную строку определенной длины.
     *
     * @param length длина строки
     * @return случайная строка
     */
    public String generateRandomString(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length must be non-negative.");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + random.nextInt(26)); // Генерирует символы a-z
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public byte[] generateRandomBytes() {
        return generateRandomBytes(DEFAULT_BYTE_ARRAY_LENGTH);
    }

    /**
     * Генерирует случайный массив байтов заданной длины.
     *
     * @param length длина массива
     * @return случайный byte[]
     */
    public byte[] generateRandomBytes(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length must be non-negative.");
        }
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * Генерирует случайное число int.
     *
     * @return случайное число int
     */
    public int generateRandomInt() {
        return random.nextInt();
    }

    /**
     * Генерирует случайное число long.
     *
     * @return случайное число long
     */
    public long generateRandomLong() {
        return random.nextLong();
    }
}
