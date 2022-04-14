package com.alkemy.peliculas.repository.specifications;

import com.alkemy.peliculas.dto.filters.PeliculaFiltersDTO;
import com.alkemy.peliculas.entity.Genero;
import com.alkemy.peliculas.entity.Pelicula;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PeliculaSpecification {

    public Specification<Pelicula> getByFilters(PeliculaFiltersDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filtersDTO.getName())) { // pregunto si tiene algo
                predicates.add(  // si tiene lo agrago al predicado
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("titulo")),
                                "%" + filtersDTO.getName().toLowerCase() + "%"
                        )
                );

            }
            if (filtersDTO.getGenre() != null) {
                Join<Genero, Pelicula> join = root.join("genero", JoinType.INNER);
                Expression<String> generoId = join.get("id");
                predicates.add(generoId.in(filtersDTO.getGenre()));
            }

            //remove duplicates
            query.distinct(true);

            // Order resolver
            String orderByField = "fechaCreacion";
            query.orderBy(
                    filtersDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)) :
                            criteriaBuilder.desc(root.get(orderByField))
            );

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
