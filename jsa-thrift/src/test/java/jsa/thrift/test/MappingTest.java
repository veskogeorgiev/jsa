package jsa.thrift.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import jsa.endpoint.thrift.DtoConverter;
import jsa.endpoint.thrift.TypeMapping;
import jsa.thrift.test.mock.api.Item;
import jsa.thrift.test.mock.api.ItemResult;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MappingTest {

    private DtoConverter converter;

    @Before
    public void setup() {
        TypeMapping typeMapping = new TypeMapping();
        typeMapping.addMapping(Item.class, jsa.thrift.test.mock.thrift.Item.class);
        typeMapping.addMapping(ItemResult.class, jsa.thrift.test.mock.thrift.ItemResult.class);

        converter = new DtoConverter(typeMapping);
    }

    @Test
    public void testConvertSimple() {
        Item item = new Item("12", "My Name Is...", 54);
        jsa.thrift.test.mock.thrift.Item res = (jsa.thrift.test.mock.thrift.Item)
                converter.convert(item, jsa.thrift.test.mock.thrift.Item.class);

        assertEqualItem(item, res);
    }

    @Test
    public void testConvertSimple1() {
        ItemResult expected = new ItemResult();
        expected.setName("My Name Is...");
        expected.setItems(new LinkedList<Item>());
        int size = 10;
        for (int i = 0; i < size; i++) {
            expected.getItems().add(new Item(Integer.toString(i), "My Name Is..." + i, i * 2));
        }

        jsa.thrift.test.mock.thrift.ItemResult res = (jsa.thrift.test.mock.thrift.ItemResult)
                converter.convert(expected, jsa.thrift.test.mock.thrift.ItemResult.class);

        Assert.assertEquals(res.getName(), expected.getName());

        for (int i = 0; i < size; i++) {
            Item i1 = expected.getItems().get(i);
            jsa.thrift.test.mock.thrift.Item i2 = res.getItems().get(i);
            assertEqualItem(i1, i2);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testConvertMap() {
        Map<String, Item> map = new HashMap<String, Item>();

        int size = 10;
        for (int i = 0; i < size; i++) {
            map.put("key" + i, new Item(Integer.toString(i), "My Name Is..." + i, i * 2));
        }

        Map<String, jsa.thrift.test.mock.thrift.Item> res = (Map<String, jsa.thrift.test.mock.thrift.Item>)
                converter.convert(map, null);

        Assert.assertEquals(map.size(), res.size());

        for (int i = 0; i < size; i++) {
            Item i1 = map.get("key" + i);
            jsa.thrift.test.mock.thrift.Item i2 = res.get("key" + i);
            assertEqualItem(i1, i2);
        }
    }

    private void assertEqualItem(Item i1, jsa.thrift.test.mock.thrift.Item i2) {
        Assert.assertEquals(i1.getId(), i2.getId());
        Assert.assertEquals(i1.getName(), i2.getName());
        Assert.assertEquals(i1.getCount(), i2.getCount());

    }
}
