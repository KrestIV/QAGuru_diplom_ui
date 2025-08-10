package models;

import com.github.javafaker.Faker;
import helpers.PetStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetModel {
    long id;
    PetCategoryModel category;
    String name;
    String[] photoUrls;
    PetTagModel[] tags;
    String status;

    public PetModel() {
        Faker faker = new Faker();

        this.id = 0L;
        this.category = new PetCategoryModel(faker.number().numberBetween(0L, Long.MAX_VALUE), faker.animal().name());
        this.name = faker.dog().name();
        this.photoUrls = new String[]{faker.internet().url(), faker.internet().url()};
        this.tags = new PetTagModel[]{new PetTagModel(faker.number().numberBetween(0L, Long.MAX_VALUE), faker.color().name())};
        this.status = faker.options().option(PetStatus.class).getStatus();
    }
}