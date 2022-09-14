package com.hibernate4all.tutorial.model;

import com.hibernate4all.tutorial.domain.Review;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ValidationReviewTest {

    private Validator validator;

    private final Logger LOGGER = LoggerFactory.getLogger(ValidationReviewTest.class);

    @BeforeAll
    public  void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void Review_ratingValidation() {
        Review review1 = new Review().setContent("Mon nouveau commentaire").setAuthor("Gildas").setRating(12);
        Set<ConstraintViolation<Review>> errors = validator.validate(review1);
        assertThat(errors).as("Seule une erreur est attendue").hasSize(1);
        ConstraintViolation<Review> constraint = errors.iterator().next();
        LOGGER.info("Property: " + constraint.getPropertyPath().toString() + " / Erreur: " + constraint.getMessage());
    }



}
