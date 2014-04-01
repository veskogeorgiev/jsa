package jsa.test.api.v2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    String name;
    int count;
    String description;

    public Item(String name, int count) {
        this(name, count, "");
    }
    
}
