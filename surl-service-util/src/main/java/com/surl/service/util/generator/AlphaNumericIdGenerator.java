package com.surl.service.util.generator;

import java.util.Random;

import com.surl.service.util.api.IdGenerator;

public final class AlphaNumericIdGenerator implements IdGenerator {

    private static final char[] ALPHA_NUMERIC = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final Random RANDOM = new Random();
    private static final int CODE_SIZE = 7;
    private static final int MAX_RANDOM_INT = ALPHA_NUMERIC.length;

    @Override
    public String nextId() {
        final StringBuilder code = new StringBuilder();
        for (int i = 0;i < CODE_SIZE;i++) {
            code.append(ALPHA_NUMERIC[RANDOM.nextInt(MAX_RANDOM_INT - 1)]);
        }
        return code.toString();
    }

}
