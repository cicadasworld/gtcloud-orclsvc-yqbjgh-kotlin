package gtcloud.yqbjgh.specification;


import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class BetweenSpecification<T> implements Specification<T> {
    private final String property;
    private final Float lower;
    private final Float upper;

    BetweenSpecification(String property, Float lower, Float upper) {
        this.property = property;
        this.lower = lower;
        this.upper = upper;
    }

    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.between(root.get(property), lower, upper);
    }
}
