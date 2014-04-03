package jsa.test.api.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Item DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    String name;
    int count;
}
