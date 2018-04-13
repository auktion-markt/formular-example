package de.auktionmarkt.formular.example;

import de.auktionmarkt.formular.specification.annotation.EntityReference;
import de.auktionmarkt.formular.specification.annotation.FormElement;
import de.auktionmarkt.formular.specification.annotation.FormInput;
import de.auktionmarkt.formular.specification.annotation.FormSubmitField;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Entity
@Data
@FormSubmitField
public class ExampleEntity {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    @FormElement
    @FormInput(label = "Name")
    private String exampleString;

    @Column
    @Max(25)
    @FormElement
    @FormInput(label = "Years (max. 25)")
    private Integer exampleNumber;

    @Column
    @FormElement
    @FormInput(label = "Instructions read")
    private Boolean exampleBoolean;

    @Column
    @FormElement
    @FormInput(label = "Invoice sent at")
    private LocalDate exampleDate;

    @Enumerated(EnumType.STRING)
    @FormElement
    @FormInput(label = "Default time calculation unit")
    private TimeUnit exampleEnum;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @FormElement
    @FormInput(label = "Applied categories")
    private Set<Locale.Category> exampleMultiEnum;

    @ManyToOne
    @FormElement
    @FormInput(label = "Parent")
    @EntityReference
    private ExampleEntity exampleEntity;

    @Override
    public String toString() {
        return "ExampleEntity{id=" + id + '}';
    }
}
