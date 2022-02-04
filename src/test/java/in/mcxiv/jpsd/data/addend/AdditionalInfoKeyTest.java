package in.mcxiv.jpsd.data.addend;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdditionalInfoKeyTest {

    @Test
    void testUniqueness() {
        List<String> list = Arrays.stream(AdditionalInfoKey.values())
                .map(AdditionalInfoKey::getValue)
                .map(String::new)
                .collect(Collectors.toList());

        HashSet<String> set = new HashSet<>(list);

        set.forEach(System.out::println);

        list.stream()
                .map(s -> new Pair<>(s, list.stream().filter(s::equals).count()))
                .filter(p -> p.b > 1)
                .map(Pair::getA)
                .forEach(System.err::println);

        assertEquals(list.size(), set.size());

    }

    public class Pair<A, B> {
        public A a;
        public B b;

        public Pair() {
        }

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }

        public A getA() {
            return a;
        }
    }

}