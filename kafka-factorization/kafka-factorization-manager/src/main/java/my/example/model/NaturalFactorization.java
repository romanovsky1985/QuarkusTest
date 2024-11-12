package my.example.model;

import java.util.ArrayList;
import java.util.List;

public class NaturalFactorization {
    private Long number = 1L;
    private List<Long> factorization = List.of(1L);

    public NaturalFactorization() {}

    public NaturalFactorization(Long number) {
        if (number < 1) {
            throw new IllegalArgumentException();
        }
        if (number == 1) {
            return;
        }
        this.number = number;
        factorization = new ArrayList<>();
        while (number > 1) {
            for (long i = 2; i <= number; i++) {
                if (number % i == 0) {
                    factorization.add(i);
                    number /= i;
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append(number)
                .append(" = ")
                .append(factorization.getFirst());
        for (int i = 1; i < factorization.size(); i++) {
            builder.append(" * ").append(factorization.get(i));
        }
        return builder.toString();
    }

    public Long getNumber() {
        return number;
    }

    public List<Long> getFactorization() {
        return factorization;
    }
}
