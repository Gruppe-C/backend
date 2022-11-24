package de.backend.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public final class NanoidGeneratorTest {
    @Test
    void test_bitRotation() {
        Assertions.assertEquals(0xFFFF_FFFF_FFFF_FFBAL, NanoidGenerator.rotl(0xBAFF_FFFF_FFFF_FFFFL, 8));
        Assertions.assertEquals((1 << 3), NanoidGenerator.rotl(1, 3));
        Assertions.assertNotEquals((0xBAFF_FFFF_FFFF_FFFFL << 8L), NanoidGenerator.rotl(0xBAFF_FFFF_FFFF_FFFFL, 8));
        Assertions.assertEquals(0xFFFF_FFFF_FFFF_FF7FL, NanoidGenerator.rotl(0x7FFF_FFFF_FFFF_FFFFL, 8));
        Assertions.assertEquals(0xFFFF_7FFF_FFFF_BABAL, NanoidGenerator.rotl(0x7FFF_FFFF_BABA_FFFFL, 8 * 6));
    }

    @Test
    void test_clamp() {
        Assertions.assertEquals(10, NanoidGenerator.clamp(10, -10, 10));
        Assertions.assertEquals(-10, NanoidGenerator.clamp(-11, -10, 10));
        Assertions.assertEquals(10, NanoidGenerator.clamp(11, -10, 10));
    }

    @Test
    void test_integrity() {
        Set<String> ids = new HashSet<String>();
        for (int i = 0; i < 100_000; ++i) {
            final String id = NanoidGenerator.getRandomNanoId(10);
            Assertions.assertEquals(10, id.length());
            Assertions.assertFalse(ids.contains(id));
            ids.add(id);
        }
    }
}
