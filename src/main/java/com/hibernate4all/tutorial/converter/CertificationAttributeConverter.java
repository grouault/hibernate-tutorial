package com.hibernate4all.tutorial.converter;

import com.hibernate4all.tutorial.domain.Certification;
import com.hibernate4all.tutorial.domain.Movie;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

// autoApply=true indique que des qu'une entité du domaine a un attribut Certification
// hibernate applique le converter
@Converter(autoApply = true)
public class CertificationAttributeConverter implements AttributeConverter<Certification, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Certification certification) {
        return certification != null ? certification.getKey(): null;
    }

    @Override
    public Certification convertToEntityAttribute(Integer dbData) {
        return Stream.of(Certification.values())
                     .filter(certif -> certif.getKey().equals(dbData))
                     .findFirst()
                     .orElse(null);
    }

}
