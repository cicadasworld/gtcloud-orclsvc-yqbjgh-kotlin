package gtcloud.yqbjgh.specification;

import org.springframework.data.jpa.domain.Specification;
import static javax.persistence.criteria.Predicate.BooleanOperator.OR;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PredicateBuilder<T> {

    private final Predicate.BooleanOperator operator;

    private List<Specification<T>> specifications;

    public PredicateBuilder(Predicate.BooleanOperator operator) {
        this.operator = operator;
        this.specifications = new ArrayList<>();
    }

    public PredicateBuilder<T> between(boolean condition, String property, Float lower, Float upper) {
        return this.predicate(condition, new BetweenSpecification<>(property, lower, upper));
    }

    public PredicateBuilder<T> like(boolean condition, String property, String pattern) {
        return this.predicate(condition, new LikeSpecification<>(property, pattern));
    }

    public PredicateBuilder<T> predicate(Specification<T> specification) {
        return this.predicate(true, specification);
    }

    private PredicateBuilder<T> predicate(boolean condition, Specification<T> specification) {
        if (condition) {
            this.specifications.add(specification);
        }
        return this;
    }

    public Specification<T> build() {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate[] predicates = new Predicate[specifications.size()];
            for (int i = 0; i < specifications.size(); i++) {
                predicates[i] = specifications.get(i).toPredicate(root, query, cb);
            }
            if (Objects.equals(predicates.length, 0)) {
                return null;
            }
            return OR.equals(operator) ? cb.or(predicates) : cb.and(predicates);
        };
    }
}
